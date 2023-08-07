package acs.upb.licenta.aplicatiegrup.popups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.User;
import acs.upb.licenta.aplicatiegrup.eventActivities.MyEventsActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.SelectedEventActivity;

public class PopDoubleCheckLeaveEvent extends AppCompatActivity {

    String eventId;
    Button no, yes;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_double_check_leave_event);

        no = findViewById(R.id.no1);
        yes = findViewById(R.id.yes1);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.2));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectedEventActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Events").child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Event event = snapshot.getValue(Event.class);
                        String notAttending = "";
                        if (event.getNotAttending() != null) {
                            notAttending = event.getNotAttending();
                        }
                        notAttending += uid + ",";
                        FirebaseDatabase.getInstance().getReference("Events")
                                .child(eventId).child("notAttending").setValue(notAttending);

                        if (event.getOwner().trim().equals(uid)) {
                            String finalNotAttending = notAttending;
                            FirebaseDatabase.getInstance().getReference("Groups").child(event.getGroupId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    Group group = snapshot.getValue(Group.class);
                                    String[] members = group.getMembers().split(",");
                                    for (String member : members) {
                                        if (!member.trim().equals("")) {
                                            boolean isAttending = true;
                                            for (String m : finalNotAttending.split(",")) {
                                                if (m.trim().equals(member.trim())) {
                                                    isAttending = false;
                                                    break;
                                                }
                                            }
                                            if (isAttending) {
                                                FirebaseDatabase.getInstance().getReference("Events")
                                                        .child(eventId).child("owner").setValue(member.trim());
                                                break;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                        }

                        FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                String message = user.getName() + " left event '" + event.getName() + "' (" +
                                        event.getGroup() + ").";
                                long time = new Date().getTime();
                                Notification notification = new Notification(message, event.getGroupId(), time, eventId, uid);
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
                startActivity(new Intent(getApplicationContext(), MyEventsActivity.class));
                finish();
            }
        });
    }
}