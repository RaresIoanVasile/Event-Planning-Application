package acs.upb.licenta.aplicatiegrup.popups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import java.util.Date;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.User;
import acs.upb.licenta.aplicatiegrup.groupActivities.MyGroupsActivity;
import acs.upb.licenta.aplicatiegrup.groupActivities.SelectedGroupActivity;

public class PopDoubleCheckLeaveGroup extends AppCompatActivity {

    String groupId;
    Button no, yes;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_double_check_leave_group);

        no = findViewById(R.id.no2);
        yes = findViewById(R.id.yes2);

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
                        Group group = snapshot.getValue(Group.class);
                        String newMembers = "";
                        for (String member : group.getMembers().split(",")) {
                            if (!member.trim().equals("") && !member.trim().equals(uid)) {
                                newMembers += member.trim() + ",";
                            }
                        }
                        if (group.getAdmin().trim().equals(uid.trim())) {
                            FirebaseDatabase.getInstance().getReference("Groups").child(groupId)
                                    .child("admin").setValue(newMembers.split(",")[0]);
                        }
                        FirebaseDatabase.getInstance().getReference("Groups").child(groupId)
                                .child("members").setValue(newMembers);

                        FirebaseDatabase.getInstance().getReference("Events").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Event event = dataSnapshot.getValue(Event.class);
                                    if (event.getGroupId().trim().equals(groupId)) {
                                        String[] notAttending = event.getNotAttending().split(",");
                                        String neww = "";
                                        for (String n : notAttending) {
                                            if (!n.trim().equals("") && !n.trim().equals(uid)) {
                                                neww += n + ",";
                                            }
                                        }
                                        FirebaseDatabase.getInstance().getReference("Events").child(event.getEventId())
                                                .child("notAttending").setValue(neww);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                String message = user.getName() + " left group (" + group.getName() + ").";
                                long time = new Date().getTime();
                                Notification notification = new Notification(message, groupId, time, "-1", uid);
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

                startActivity(new Intent(getApplicationContext(), MyGroupsActivity.class));
                finish();
            }
        });
    }
}