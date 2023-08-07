package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class CreateEventActivity extends AppCompatActivity {

    private Button createEvent;
    private Spinner spinner;
    private EditText eventName, eventLocation;
    private TextView selectedDate;
    private String date;
    private ImageButton backButton;
    private Button hourButton;
    int hour, minute;
    int position;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Bundle bundle = getIntent().getExtras();
        date = "";
        if (bundle != null) {
            date = bundle.getString("date");
        }

        createEvent = findViewById(R.id.buttonCreateEventPart2);
        spinner = findViewById(R.id.spinnerGroupName);
        eventName = findViewById(R.id.eventName);
        eventLocation = findViewById(R.id.eventLocation);
        selectedDate = findViewById(R.id.chosenDate);
        backButton = findViewById(R.id.back);
        hourButton = findViewById(R.id.chosenHour);

        selectedDate.setText("Chosen date: " + date);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Groups");
        ArrayList<String> groups = new ArrayList<>();
        ArrayList<String> groupIds = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Group group = dataSnapshot.getValue(Group.class);
                    String membersStr = group.getMembers();
                    String[] members = membersStr.split(",");
                    boolean isPart = false;
                    for (String member : members) {
                        if (uid.equals(member)) {
                            isPart = true;
                            break;
                        }
                    }
                    if (isPart) {
                        groups.add(group.getName());
                        groupIds.add(dataSnapshot.getKey());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateEventActivity.this,
                        android.R.layout.simple_spinner_item, groups);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(eventName.getText()).trim();
                String location = String.valueOf(eventLocation.getText()).trim();
                String group = spinner.getSelectedItem().toString().trim();
                position = 0;
                for (String groupName : groups) {
                    System.out.println(groupName + " " + groups.indexOf(groupName));
                    if (groupName.trim().equals(group.trim())) {
                        position = groups.indexOf(groupName);
                        break;
                    }
                }
                String eventId = getAlphaNumericString(15);
                String time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                Event event = new Event(date, name, group, uid, location, groupIds.get(position), eventId, time);

                FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        String message = "Event '" + name + "' created by " + user.getName() + " (" + group + ").";
                        long time = new Date().getTime();
                        Notification notification = new Notification(message, groupIds.get(position), time, eventId, uid);
                        FirebaseDatabase.getInstance().getReference("Notifications").child(Long.toString(time)).setValue(notification);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                FirebaseDatabase.getInstance().getReference("Events").child(eventId).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateEventActivity.this, "Event was created.", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference("EventsCreated").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    int value = snapshot.getValue(Integer.class) + 1;
                                    FirebaseDatabase.getInstance().getReference("EventsCreated").setValue(value);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                            startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        } else {
                            Toast.makeText(CreateEventActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
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

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                hourButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener,
                hour, minute, true);
        timePickerDialog.setTitle("Selected Time");
        timePickerDialog.show();
    }
}