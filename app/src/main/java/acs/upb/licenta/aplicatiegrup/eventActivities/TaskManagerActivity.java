package acs.upb.licenta.aplicatiegrup.eventActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.TaskAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Task;
import acs.upb.licenta.aplicatiegrup.popups.PopAddTask;

public class TaskManagerActivity extends AppCompatActivity {

    String eventId;
    Button addTask;
    ImageButton back;
    ListView listView;
    ArrayList<Task> tasks;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        Bundle bundle = getIntent().getExtras();
        eventId = "";
        if (bundle != null) {
            eventId = bundle.getString("eventId");
        }

        addTask = findViewById(R.id.addTaskButton);
        back = findViewById(R.id.backSelectedEvent5);
        listView = findViewById(R.id.taskList);

        tasks = new ArrayList<>();

        adapter = new TaskAdapter(this, tasks);

        FirebaseDatabase.getInstance().getReference("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (tasks.size() > 0) {
                    tasks.removeAll(tasks);
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);
                    if (task.getEventId().equals(eventId)) {
                        tasks.add(task);
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tasks.sort(new Comparator<Task>() {
                        @Override
                        public int compare(Task task, Task t1) {
                            return (int) t1.getTime() - (int) task.getTime();
                        }
                    });
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        listView.setAdapter(adapter);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopAddTask.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
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
}