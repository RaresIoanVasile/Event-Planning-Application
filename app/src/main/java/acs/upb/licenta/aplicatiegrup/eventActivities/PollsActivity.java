package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.PollListAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Poll;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class PollsActivity extends AppCompatActivity {

    String eventId;
    ImageButton back;
    Button createPoll;
    TextView title;

    RecyclerView recyclerView;
    PollListAdapter adapter;
    DatabaseReference databaseReference2;
    PollListAdapter.RecyclerViewClickListener listener;
    ArrayList<Poll> polls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls);

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        back = findViewById(R.id.backSelectedEvent4);
        title = findViewById(R.id.titleEventName3);
        createPoll = findViewById(R.id.createPoll);

        recyclerView = findViewById(R.id.list_of_polls);
        recyclerView.setHasFixedSize(false);
        setOnClickListener();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PollListAdapter(this, polls, listener);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Events").child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Event event = snapshot.getValue(Event.class);
                title.setText(event.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Polls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (polls.size() > 0) {
                    polls.removeAll(polls);
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    System.out.println(dataSnapshot.getValue(Poll.class).getEventId());
                    System.out.println(eventId.trim());
                    if (dataSnapshot.getValue(Poll.class).getEventId().trim().equals(eventId.trim())) {
                        polls.add(dataSnapshot.getValue(Poll.class));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectedEventActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        });

        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().trim();
                FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user.getKid().equals("0")) {
                            Intent intent = new Intent(getApplicationContext(), CreatePollActivity.class);
                            intent.putExtra("eventId", eventId);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(PollsActivity.this, "Kids cannot create polls!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });
    }

    private void setOnClickListener() {
        listener = new PollListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String pollId = polls.get(position).getPollId();
                Intent intent;
                intent = new Intent(getApplicationContext(), ResultPollActivity.class);
                intent.putExtra("pollId", pollId);
                startActivity(intent);
                finish();
            }
        };
    }
}