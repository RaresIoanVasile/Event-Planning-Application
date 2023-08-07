package acs.upb.licenta.aplicatiegrup.groupActivities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.classes.User;
import acs.upb.licenta.aplicatiegrup.eventActivities.CalendarActivity;
import acs.upb.licenta.aplicatiegrup.others.DrawerBaseActivity;
import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.GroupAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.databinding.ActivityMyGroupsBinding;
import acs.upb.licenta.aplicatiegrup.popups.PopCreateGroup;
import acs.upb.licenta.aplicatiegrup.popups.PopJoinGroup;

public class MyGroupsActivity extends DrawerBaseActivity {

    ActivityMyGroupsBinding activityMyGroupsBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button createButton, joinButton;
    private String uid;

    RecyclerView recyclerView;
    GroupAdapter adapter;
    ArrayList<Group> groups;
    GroupAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyGroupsBinding = ActivityMyGroupsBinding.inflate(getLayoutInflater());
        setContentView(activityMyGroupsBinding.getRoot());
        allocateActivityTitle("My Groups");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Groups");
        uid = firebaseAuth.getCurrentUser().getUid();

        createButton = findViewById(R.id.create_group);
        joinButton = findViewById(R.id.join_group);
        recyclerView = findViewById(R.id.groupList);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        groups = new ArrayList<>();
        adapter = new GroupAdapter(this, groups, listener);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (groups.size() != 0) {
                    groups.removeAll(groups);
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Group group = dataSnapshot.getValue(Group.class);
                    String[] members = group.getMembers().split(",");
                    for (String member : members) {
                        if (member.equals(uid)) {
                            groups.add(group);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user.getKid().equals("0")) {
                            Intent intent = new Intent(getApplicationContext(), PopCreateGroup.class);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(MyGroupsActivity.this, "Kids cannot create groups!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopJoinGroup.class);
                startActivity(intent);
            }
        });
    }

    private void setOnClickListener() {
        listener = new GroupAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), SelectedGroupActivity.class);
                String groupId = groups.get(position).getCode();
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                finish();
            }
        };
    }
}