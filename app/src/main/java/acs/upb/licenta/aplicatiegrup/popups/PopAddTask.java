package acs.upb.licenta.aplicatiegrup.popups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.Task;
import acs.upb.licenta.aplicatiegrup.classes.User;
import acs.upb.licenta.aplicatiegrup.eventActivities.TaskManagerActivity;

public class PopAddTask extends AppCompatActivity {

    String eventId;
    EditText taskName;
    Button addTask;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_add_task);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        taskName = findViewById(R.id.taskName);
        addTask = findViewById(R.id.addTaskButton2);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Events").child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Event event = snapshot.getValue(Event.class);
                        if (event.getOwner().trim().equals(uid)) {
                            String taskNameStr = taskName.getText().toString();
                            if (taskNameStr.trim().equals("")) {
                                Toast toast = Toast.makeText(PopAddTask.this, "The name of the task cannot be empty!", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                String id = getAlphaNumericString(15);
                                long time = new Date().getTime();
                                Task task = new Task(id, eventId, taskNameStr.trim(), "", "false", "", time);
                                FirebaseDatabase.getInstance().getReference("Tasks").child(id).setValue(task);

                                FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        User user = snapshot.getValue(User.class);
                                        String message = "New task '" + taskNameStr.trim() + "' added by " + user.getName() +
                                                " (" + event.getName() + ", " + event.getGroup() + ").";
                                        Notification notification = new Notification(message, event.getGroupId(), time, eventId, uid);
                                        FirebaseDatabase.getInstance().getReference("Notifications").child(Long.toString(time)).setValue(notification);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });

                                Intent intent = new Intent(getApplicationContext(), TaskManagerActivity.class);
                                intent.putExtra("eventId", eventId);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast toast = Toast.makeText(PopAddTask.this, "Only the owner of the event can organize tasks!", Toast.LENGTH_LONG);
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