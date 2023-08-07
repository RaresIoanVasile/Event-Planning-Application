package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import acs.upb.licenta.aplicatiegrup.classes.User;
import acs.upb.licenta.aplicatiegrup.others.DrawerBaseActivity;
import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.EventCalendarAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.databinding.ActivityCalendarBinding;

public class CalendarActivity extends DrawerBaseActivity {

    ActivityCalendarBinding activityCalendarBinding;
    private Button createEvent;
    private CalendarView calendarView;
    String formattedDate;
    ArrayList<String> groupIds = new ArrayList<>();

    private RecyclerView recyclerView;
    private ArrayList<Event> events = new ArrayList<>();
    private EventCalendarAdapter eventCalendarAdapter;
    private EventCalendarAdapter.RecyclerViewClickListener listener;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCalendarBinding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(activityCalendarBinding.getRoot());
        allocateActivityTitle("Calendar");

        calendarView = findViewById(R.id.calendarView);
        createEvent = findViewById(R.id.buttonCreateEvent);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        setOnClickListener();
        eventCalendarAdapter = new EventCalendarAdapter(this, events, listener);
        recyclerView.setAdapter(eventCalendarAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        databaseReference2 = firebaseDatabase.getReference("Groups");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey();
                    Group group = dataSnapshot.getValue(Group.class);
                    String[] members = group.getMembers().split(",");
                    for (String member : members) {
                        if (member.trim().equals(uid.trim())) {
                            groupIds.add(id.trim());
                        }
                    }
                }
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (events.size() != 0) {
                            events.removeAll(events);
                        }
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Event event = dataSnapshot.getValue(Event.class);
                            System.out.println(groupIds.size());
                            if (groupIds.contains(event.getGroupId().trim())) {
                                boolean notAttend = false;
                                if (event.getNotAttending() != null) {
                                    String[] notAttending = event.getNotAttending().split(",");
                                    for (String p : notAttending) {
                                        if (p.trim().equals(uid.trim())) {
                                            notAttend = true;
                                        }
                                    }
                                }
                                if (!notAttend) {
                                    events.add(event);
                                }
                            }

                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            events.sort(new Comparator<Event>() {
                                @Override
                                public int compare(Event event, Event t1) {
                                    String[] date1 = event.getDate().split("/");
                                    String[] date2 = t1.getDate().split("/");
                                    if (!date1[2].equals(date2[2])) {
                                        return Integer.parseInt(date1[2]) - Integer.parseInt(date2[2]);
                                    } else {
                                        if (!date1[1].equals(date2[1])) {
                                            return Integer.parseInt(date1[1]) - Integer.parseInt(date2[1]);
                                        } else {
                                            if (!date1[0].equals(date2[0])) {
                                                return Integer.parseInt(date1[0]) - Integer.parseInt(date2[0]);
                                            }
                                        }
                                    }
                                    return 0;
                                }
                            });
                        }
                        eventCalendarAdapter.notifyDataSetChanged();
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

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                i1++;
                String date = i + "/" + i1 + "/" + i2;
                DateFormat dateFormat;
                dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                formattedDate = "";
                try {
                    Date d = dateFormat.parse(date);
                    String newFormat = "dd/MM/yyyy";
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat);
                    if (d != null) {
                        formattedDate = newDateFormat.format(d);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user.getKid().equals("0")) {
                            if (formattedDate == null) {
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                formattedDate = df.format(c);
                            }
                            Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("date", formattedDate);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(CalendarActivity.this, "Kids cannot create events!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });
    }

    private void setOnClickListener() {
        listener = new EventCalendarAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), SelectedEventActivity.class);
                String eventId = events.get(position).getEventId();
                intent.putExtra("eventId", eventId);
                startActivity(intent);
                finish();
            }
        };
    }
}