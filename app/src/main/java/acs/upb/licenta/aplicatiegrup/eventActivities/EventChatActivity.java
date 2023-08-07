package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.things.device.TimeManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.ChatAdapter;
import acs.upb.licenta.aplicatiegrup.classes.ChatMessage;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class EventChatActivity extends AppCompatActivity {

    static String eventId;
    ImageButton fab;
    ImageButton back;
    TextView title;
    EditText input;
    String userName;
    String uid;
    DatabaseReference databaseReference1, databaseReference2, databaseReference3;
    static Toast t;
    static Context context;
    RecyclerView recyclerView;
    ChatAdapter adapter;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    String group, groupId, eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_chat);
        context = getApplicationContext();

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        fab = findViewById(R.id.fab);
        input = findViewById(R.id.inputMessage);
        back = findViewById(R.id.backSelectedEvent3);
        recyclerView = findViewById(R.id.list_of_messages);
        title = findViewById(R.id.titleEventName2);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference3 = FirebaseDatabase.getInstance().getReference("Events");
        databaseReference3.addValueEventListener(new ValueEventListener() {
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

        databaseReference2 = FirebaseDatabase.getInstance().getReference("Messages");
        adapter = new ChatAdapter(this, messages);
        recyclerView.setAdapter(adapter);

        if (databaseReference2 != null) {
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (messages.size() != 0) {
                        messages.removeAll(messages);
                    }
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.getValue(ChatMessage.class).getEventId().trim().equals(eventId.trim())) {
                            ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                            messages.add(chatMessage);
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        messages.sort(new Comparator<ChatMessage>() {
                            @Override
                            public int compare(ChatMessage chatMessage, ChatMessage t1) {
                                return (int) t1.getMessageTime() - (int) chatMessage.getMessageTime();
                            }
                        });
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference1.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userName = snapshot.getValue(User.class).getName();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText() != null) {
                    if (!input.getText().equals("")) {
                        databaseReference2.push().setValue(new ChatMessage(eventId, input.getText().toString(), userName));

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
                                        String message = "New message from " + user.getName() + " (" + eventName +
                                                ", " + group + ").";
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

                        adapter.notifyDataSetChanged();
                    }
                } else {
                    makeToast("Empty message!");
                }
                input.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectedEventActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        });
    }

    private static void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }
}