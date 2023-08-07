package acs.upb.licenta.aplicatiegrup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import acs.upb.licenta.aplicatiegrup.adapters.NotificationAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.databinding.ActivityMainMenu14Binding;
import acs.upb.licenta.aplicatiegrup.others.DrawerBaseActivity;
import acs.upb.licenta.aplicatiegrup.others.MyBackgroundService;

public class MainMenuActivity extends DrawerBaseActivity {

    ActivityMainMenu14Binding activityMainMenu14Binding;
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    ArrayList<Notification> notifications;
    ArrayList<String> groupIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainMenu14Binding = ActivityMainMenu14Binding.inflate(getLayoutInflater());
        setContentView(activityMainMenu14Binding.getRoot());
        allocateActivityTitle("Main Menu");

        Intent serviceIntent = new Intent(this, MyBackgroundService.class);
        startService(serviceIntent);

        recyclerView = findViewById(R.id.notificationList);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupIds = new ArrayList<>();
        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(this, notifications);
        recyclerView.setAdapter(adapter);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (groupIds.size() != 0) {
                    groupIds.removeAll(groupIds);
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Group group = dataSnapshot.getValue(Group.class);
                    String[] members = group.getMembers().split(",");
                    for (String member : members) {
                        if (member.trim().equals(uid)) {
                            groupIds.add(group.getCode());
                        }
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
                if (notifications.size() != 0) {
                    notifications.removeAll(notifications);
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    if (groupIds.contains(notification.getGroupId()) && !notification.getUid().equals(uid)) {
                        notifications.add(notification);
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    notifications.sort(new Comparator<Notification>() {
                        @Override
                        public int compare(Notification notification, Notification t1) {
                            return (int)t1.getTime() - (int)notification.getTime();
                        }
                    });
                }

                if (notifications.size() > 50) {
                    notifications.subList(50, notifications.size()).clear();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}