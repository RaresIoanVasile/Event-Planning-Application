package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.MembersAdapter;
import acs.upb.licenta.aplicatiegrup.adapters.ParticipantsAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class ParticipantsActivity extends AppCompatActivity {

    String eventId;
    TextView title;
    RecyclerView recyclerView;
    ImageButton back;

    ParticipantsAdapter adapter;
    ArrayList<User> participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        title = findViewById(R.id.titleEventName4);
        back = findViewById(R.id.backSelectedEvent7);
        recyclerView = findViewById(R.id.participants);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        participants = new ArrayList<>();
        adapter = new ParticipantsAdapter(this, participants);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Events").child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (participants.size() > 0) {
                    participants.removeAll(participants);
                }
                Event event = snapshot.getValue(Event.class);
                title.setText(event.getName().trim());
                FirebaseDatabase.getInstance().getReference("Groups").child(event.getGroupId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Group group = snapshot.getValue(Group.class);
                        String[] members = group.getMembers().split(",");
                        String[] notAttending = event.getNotAttending().split(",");
                        for (String member : members) {
                            boolean attending = true;
                            if (!member.trim().equals("")) {
                                for (String not : notAttending) {
                                    if (member.trim().equals(not.trim())) {
                                        attending = false;
                                        break;
                                    }
                                }
                                if (attending) {
                                    FirebaseDatabase.getInstance().getReference("Users").child(member.trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            User user = snapshot.getValue(User.class);
                                            participants.add(user);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
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
    }
}