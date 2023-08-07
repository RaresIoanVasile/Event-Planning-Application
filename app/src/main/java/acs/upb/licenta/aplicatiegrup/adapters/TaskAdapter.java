package acs.upb.licenta.aplicatiegrup.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.Task;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class TaskAdapter extends ArrayAdapter<Task> {
    ArrayList<Task> tasks;
    Context context;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, R.layout.task_item, tasks);
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.task_item, null);
            TextView item = convertView.findViewById(R.id.task);
            Button takeOn = convertView.findViewById(R.id.takeOn);
            CheckBox checkBox = convertView.findViewById(R.id.checked2);
            TextView assigned = convertView.findViewById(R.id.assigned);

            if (tasks.get(position).getAssigned().equals("")) {
                takeOn.setVisibility(View.VISIBLE);
                assigned.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
            } else {
                takeOn.setVisibility(View.INVISIBLE);
                assigned.setVisibility(View.VISIBLE);
                assigned.setText(tasks.get(position).getAssigned());
                checkBox.setVisibility(View.VISIBLE);
            }

            if (tasks.get(position).getChecked().equals("true")) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (tasks.get(position).getUid().equals(uid)) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                if (tasks.get(position).getChecked().equals("true")) {
                    checkBox.setVisibility(View.VISIBLE);
                    checkBox.setClickable(false);
                } else {
                    checkBox.setVisibility(View.INVISIBLE);
                }
            }

            item.setText(tasks.get(position).getTaskName());

            takeOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Task task = tasks.get(position);
                    FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            if (user.getKid().equals("0")) {
                                task.setAssigned(user.getName().trim());
                                task.setUid(user.getUid());
                                FirebaseDatabase.getInstance().getReference("Tasks").child(task.getTaskId()).setValue(task);

                                FirebaseDatabase.getInstance().getReference("Events").child(task.getEventId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        Event event = snapshot.getValue(Event.class);
                                        String message = "Task '" + task.getTaskName() + "' has been assigned to " + task.getAssigned()
                                                + " (" + event.getName() + ", " + event.getGroup() + ").";
                                        long time = new Date().getTime();
                                        Notification notification = new Notification(message, event.getGroupId(), time, event.getEventId(), uid);
                                        FirebaseDatabase.getInstance().getReference("Notifications").child(Long.toString(time)).setValue(notification);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                            } else {
                                Toast toast = Toast.makeText(context, "Kids cannot take on tasks!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
            });

            if (checkBox.isClickable()) {
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Task task = tasks.get(position);
                        if (uid.equals(task.getUid())) {
                            if (task.getChecked().equals("true")) {
                                FirebaseDatabase.getInstance().getReference("Tasks").child(task.getTaskId()).child("checked").setValue("false");
                            } else {
                                FirebaseDatabase.getInstance().getReference("Tasks").child(task.getTaskId()).child("checked").setValue("true");
                                FirebaseDatabase.getInstance().getReference("Events").child(task.getEventId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        Event event = snapshot.getValue(Event.class);
                                        String message = "Task '" + task.getTaskName() + "' has been completed by " + task.getAssigned()
                                                + " (" + event.getName() + ", " + event.getGroup() + ").";
                                        long time = new Date().getTime();
                                        Notification notification = new Notification(message, event.getGroupId(), time, event.getEventId(), uid);
                                        FirebaseDatabase.getInstance().getReference("Notifications").child(Long.toString(time)).setValue(notification);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                            }
                        } else {
                            Toast toast = Toast.makeText(context, "Not your task!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
        }
        return convertView;
    }

}
