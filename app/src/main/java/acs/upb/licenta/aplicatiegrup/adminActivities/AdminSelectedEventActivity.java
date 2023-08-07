package acs.upb.licenta.aplicatiegrup.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.eventActivities.EventChatActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.ExpenseTrackerActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.MyEventsActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.PollsActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.SelectedEventActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.ShoppingListActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.TaskManagerActivity;
import acs.upb.licenta.aplicatiegrup.popups.PopDoubleCheckDeleteEvent;
import acs.upb.licenta.aplicatiegrup.popups.PopDoubleCheckLeaveEvent;

public class AdminSelectedEventActivity extends AppCompatActivity {

    String eventId;
    CardView deleteEvent, chatEvent;
    TextView title;
    ImageButton backButton;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_selected_event);

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        deleteEvent = findViewById(R.id.deleteEvent);
        chatEvent = findViewById(R.id.chatEvent);
        title = findViewById(R.id.titleEventName);
        backButton = findViewById(R.id.backSelectedEvent);

        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().trim().equals(eventId.trim())) {
                        title.setText(dataSnapshot.getValue(Event.class).getName());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminEventsActivity.class));
            }
        });

        chatEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminEventChatActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        });

        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference("Events").child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Event event = snapshot.getValue(Event.class);
                        Intent intent = new Intent(getApplicationContext(), PopDoubleCheckDeleteEvent.class);
                        intent.putExtra("eventId", eventId);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });
    }
}