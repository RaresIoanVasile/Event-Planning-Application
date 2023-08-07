package acs.upb.licenta.aplicatiegrup.others;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import acs.upb.licenta.aplicatiegrup.classes.ChatMessage;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.Poll;

public class MyBackgroundService extends Service {
    private Handler mHandler;
    private Runnable mRunnable;

    String eventId;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 60 * 1000); // Repeat every 60 seconds
                FirebaseDatabase.getInstance().getReference("Events").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Event event = dataSnapshot.getValue(Event.class);
                            eventId = event.getEventId();
                            Date today = Calendar.getInstance().getTime();
                            Date eventDate = null;
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            try {
                                eventDate = sdf.parse(event.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long diffInMillies = today.getTime() - eventDate.getTime();
                            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                            if (diff > 7) {
                                dataSnapshot.getRef().removeValue();

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

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Groups");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Group group = dataSnapshot.getValue(Group.class);
                            if (group.getMembers().trim().equals("")) {
                                dataSnapshot.getRef().removeValue();

                                FirebaseDatabase.getInstance().getReference("Events").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                            Event event = dataSnapshot1.getValue(Event.class);
                                            eventId = event.getEventId();
                                            if (event.getGroupId().equals(group.getCode())) {
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
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.post(mRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}