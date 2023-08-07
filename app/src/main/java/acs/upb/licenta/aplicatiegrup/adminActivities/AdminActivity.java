package acs.upb.licenta.aplicatiegrup.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import acs.upb.licenta.aplicatiegrup.R;

public class AdminActivity extends AppCompatActivity {

    TextView users, groups, events, messages, polls, allUsers, allGroups, allEvents;
    Button viewAllGroups, viewAllEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        users = findViewById(R.id.usersTextView);
        groups = findViewById(R.id.groupsTextView);
        events = findViewById(R.id.eventsTextView);
        messages = findViewById(R.id.msjTextView);
        polls = findViewById(R.id.pollsTextView);
        allUsers = findViewById(R.id.allUsersTextView);
        allGroups = findViewById(R.id.allGroupsTextView);
        allEvents = findViewById(R.id.allEventsTextView);
        viewAllGroups = findViewById(R.id.btnAdminGroups);
        viewAllEvents = findViewById(R.id.btnAdminEvents);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 = db.getReference("Users");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                users.setText("Number of current users: " + String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        DatabaseReference databaseReference6 = db.getReference("UsersCreated");
        databaseReference6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int count = snapshot.getValue(Integer.class);
                allUsers.setText("Number of all time created users: " + String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        DatabaseReference databaseReference2 = db.getReference("Groups");
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                groups.setText("Number of current groups: " + String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        DatabaseReference databaseReference7 = db.getReference("GroupsCreated");
        databaseReference7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int count = snapshot.getValue(Integer.class);
                allGroups.setText("Number of all time created groups: " + String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        DatabaseReference databaseReference3 = db.getReference("Events");
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                events.setText("Number of current events: " + String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        DatabaseReference databaseReference8 = db.getReference("EventsCreated");
        databaseReference8.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int count = snapshot.getValue(Integer.class);
                allEvents.setText("Number of all time created events: " + String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        DatabaseReference databaseReference4 = db.getReference("Polls");
        databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                polls.setText("Number of current polls: " + String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        DatabaseReference databaseReference5 = db.getReference("Messages");
        databaseReference5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                messages.setText("Number of current messages: " + String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        viewAllGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminGroupsActivity.class));
            }
        });

        viewAllEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminEventsActivity.class));
            }
        });

    }
}