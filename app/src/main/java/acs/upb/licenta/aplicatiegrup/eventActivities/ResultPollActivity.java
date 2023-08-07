package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.PollOptionsAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Poll;

public class ResultPollActivity extends AppCompatActivity {

    static String pollId;
    ImageButton back;
    static TextView pollName, pollDescription;
    RecyclerView recyclerView;
    static PollOptionsAdapter adapter;
    Poll poll;
    static String eventId;
    PollOptionsAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_poll);

        Bundle bundle = getIntent().getExtras();
        pollId = "";
        if (bundle != null) {
            pollId = bundle.getString("pollId");
        }

        back = findViewById(R.id.backToPolls3);
        pollName = findViewById(R.id.pollName3);
        pollDescription = findViewById(R.id.pollDescription3);
        recyclerView = findViewById(R.id.poll_options2);

        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseDatabase.getInstance().getReference("Polls").child(pollId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                poll = snapshot.getValue(Poll.class);
                eventId = poll.getEventId();
                pollName.setText(poll.getPollName());
                pollDescription.setText(poll.getDescription());
                adapter = new PollOptionsAdapter(ResultPollActivity.this, poll, listener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        setOnClickListener();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PollsActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setOnClickListener() {
        listener = new PollOptionsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                ArrayList<Integer> percentages = new ArrayList<>();
                List<String > options = Arrays.asList(poll.getOptions().trim().split(","));
                List<String> perc = Arrays.asList(poll.getPercentages().trim().split(","));
//                for (String pp : perc) {
//                    percentages.add(Integer.parseInt(pp));
//                }
//
//                percentages.set(position, percentages.get(position) + 1);
                for (int i = 0; i < perc.size(); i++) {
                    if (i == position) {
                        percentages.add(Integer.parseInt(perc.get(i)) + 1);
                    } else {
                        percentages.add(Integer.parseInt(perc.get(i)));
                    }
                }
                String newPercentages = "";
                for (int p : percentages) {
                    System.out.println(p);
                    newPercentages += Integer.toString(p) + ",";
                }
                newPercentages = newPercentages.substring(0, newPercentages.length() - 1);

                String voters = poll.getVoters();
                if (voters == null) {
                    voters = "";
                }
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                voters += uid + ",";

                FirebaseDatabase.getInstance().getReference("Polls").child(pollId).child("voters").setValue(voters);
                FirebaseDatabase.getInstance().getReference("Polls").child(pollId).child("percentages").setValue(newPercentages);
                Intent intent = new Intent(getApplicationContext(), ResultPollActivity.class);
                intent.putExtra("pollId", pollId);
                startActivity(intent);
                finish();
            }
        };
    }
}