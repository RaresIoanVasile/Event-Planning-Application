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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.ChatMessage;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.Poll;
import acs.upb.licenta.aplicatiegrup.eventActivities.SelectedEventActivity;
import acs.upb.licenta.aplicatiegrup.groupActivities.MyGroupsActivity;
import acs.upb.licenta.aplicatiegrup.groupActivities.SelectedGroupActivity;

public class PopDoubleCheckDeleteGroup extends AppCompatActivity {

    String groupId;
    Button no, yes;
    String uid;
    String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_double_check_delete_group);

        no = findViewById(R.id.no3);
        yes = findViewById(R.id.yes3);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        groupId = "";
        if (bundle != null) {
            groupId = bundle.getString("groupId");
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
                Intent intent = new Intent(getApplicationContext(), SelectedGroupActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Groups").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                FirebaseDatabase.getInstance().getReference("Events").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            Event event = dataSnapshot1.getValue(Event.class);
                            eventId = event.getEventId();
                            if (event.getGroupId().equals(groupId)) {
                                dataSnapshot1.getRef().removeValue();

                                FirebaseDatabase.getInstance().getReference("Polls").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                            Poll poll = dataSnapshot1.getValue(Poll.class);
                                            if (poll.getEventId().equals(eventId)) {
                                                dataSnapshot1.getRef().removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });

                                FirebaseDatabase.getInstance().getReference("Messages").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                                            ChatMessage chatMessage = dataSnapshot2.getValue(ChatMessage.class);
                                            if (chatMessage.getEventId().equals(eventId)) {
                                                dataSnapshot2.getRef().removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });

                                FirebaseDatabase.getInstance().getReference("Notifications").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot3 : snapshot.getChildren()) {
                                            Notification notification = dataSnapshot3.getValue(Notification.class);
                                            if (notification.getEventId().equals(eventId)) {
                                                dataSnapshot3.getRef().removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                Intent intent = new Intent(getApplicationContext(), MyGroupsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}