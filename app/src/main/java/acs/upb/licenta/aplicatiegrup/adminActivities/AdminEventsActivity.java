package acs.upb.licenta.aplicatiegrup.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.EventAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.databinding.ActivityMyEventsBinding;
import acs.upb.licenta.aplicatiegrup.eventActivities.SelectedEventActivity;

public class AdminEventsActivity extends AppCompatActivity {

    ActivityMyEventsBinding activityMyEventsBinding;
    RecyclerView recyclerView;

    ArrayList<Event> events = new ArrayList<>();
    ArrayList<String> groupIds = new ArrayList<>();
    EventAdapter eventAdapter;
    EventAdapter.RecyclerViewClickListener listener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_events);
        recyclerView = findViewById(R.id.recyclerViewEvents2);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setOnClickListener();
        eventAdapter = new EventAdapter(this, events, listener);
        recyclerView.setAdapter(eventAdapter);

        back = findViewById(R.id.backAdminEvents);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        databaseReference2 = firebaseDatabase.getReference("Groups");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey();
                    groupIds.add(id.trim());
                }
                if (events.size() != 0) {
                    events.removeAll(events);
                }
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (events.size() > 0) {
                            events.removeAll(events);
                        }
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Event event = dataSnapshot.getValue(Event.class);
                            System.out.println(groupIds.size());
                            events.add(event);

                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            events.sort(new Comparator<Event>() {
                                @Override
                                public int compare(Event event, Event t1) {
                                    String[] date1 = event.getDate().split("/");
                                    String[] date2 = t1.getDate().split("/");
                                    if (!date1[2].equals(date2[2])) {
                                        return Integer.parseInt(date1[2]) - Integer.parseInt(date2[2]);
                                    } else {
                                        if (!date1[1].equals(date2[1])) {
                                            return Integer.parseInt(date1[1]) - Integer.parseInt(date2[1]);
                                        } else {
                                            if (!date1[0].equals(date2[0])) {
                                                return Integer.parseInt(date1[0]) - Integer.parseInt(date2[0]);
                                            }
                                        }
                                    }
                                    return 0;
                                }
                            });
                        }
                        eventAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();
            }
        });
    }

    private void setOnClickListener() {
        listener = new EventAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), AdminSelectedEventActivity.class);
                String eventId = events.get(position).getEventId();
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        };
    }
}