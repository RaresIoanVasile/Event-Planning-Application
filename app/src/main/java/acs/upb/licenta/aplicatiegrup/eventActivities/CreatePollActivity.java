package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.Poll;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class CreatePollActivity extends AppCompatActivity {

    String eventId;
    ImageButton back;
    Button createPoll;
    EditText name, description, options;
    String groupId, group, eventName;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        back = findViewById(R.id.backToPolls);
        createPoll = findViewById(R.id.buttonCreatePollPart2);
        name = findViewById(R.id.pollName);
        description = findViewById(R.id.pollDescription);
        options = findViewById(R.id.pollOptions);

        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getAlphaNumericString(15);
                String[] optionsStr = options.getText().toString().trim().split(",");
                String optionsGood = "";
                String percentages = "";
                for (String s : optionsStr) {
                    if (s != null) {
                        if (!s.equals("")) {
                            optionsGood += s.trim() + ",";
                            percentages += "0,";
                        }
                    }
                }
                optionsGood = optionsGood.substring(0, optionsGood.length() - 1);
                percentages = percentages.substring(0, percentages.length() - 1);
                Poll poll = new Poll(id, name.getText().toString().trim(),
                        description.getText().toString().trim(),
                        eventId, optionsGood, percentages);
                databaseReference = FirebaseDatabase.getInstance().getReference("Polls");
                databaseReference.child(id).setValue(poll);

                FirebaseDatabase.getInstance().getReference("Events").child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Event event = snapshot.getValue(Event.class);
                        group = event.getGroup();
                        groupId = event.getGroupId();
                        eventName = event.getName();

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                String message = "Poll '" + name.getText().toString().trim() + "' created by " +
                                        user.getName() + " (" + eventName + ", " + group + ").";
                                long time = new Date().getTime();
                                Notification notification = new Notification(message, groupId, time, eventId, uid);
                                FirebaseDatabase.getInstance().getReference("Notifications").child(Long.toString(time)).setValue(notification);
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

                Intent intent = new Intent(getApplicationContext(), PollsActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        });

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

    static String getAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length()* Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}