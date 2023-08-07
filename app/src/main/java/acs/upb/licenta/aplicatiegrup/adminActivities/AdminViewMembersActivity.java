package acs.upb.licenta.aplicatiegrup.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.adapters.MembersAdapter;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.User;
import acs.upb.licenta.aplicatiegrup.groupActivities.MemberProfileActivity;
import acs.upb.licenta.aplicatiegrup.groupActivities.SelectedGroupActivity;

public class AdminViewMembersActivity extends AppCompatActivity {

    TextView groupName;
    ImageButton back;
    RecyclerView recyclerView;
    String groupId;

    MembersAdapter adapter;
    ArrayList<User> members;
    MembersAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_members);
        groupName = findViewById(R.id.titleGroup2);
        back = findViewById(R.id.backSelectedGroupMembers);
        recyclerView = findViewById(R.id.members);

        Bundle bundle = getIntent().getExtras();
        groupId = "";
        if (bundle != null) {
            groupId = bundle.getString("groupId");
        }

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        members = new ArrayList<>();
        adapter = new MembersAdapter(this, members, listener);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Groups").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (members.size() != 0) {
                    members.removeAll(members);
                }
                Group group = snapshot.getValue(Group.class);
                groupName.setText(group.getName());
                String membersStr = group.getMembers();
                for (String member : membersStr.split(",")) {
                    if (!member.trim().equals("")) {
                        FirebaseDatabase.getInstance().getReference("Users").child(member.trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                members.add(user);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminSelectedGroupActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                finish();
            }
        });
    }
    private void setOnClickListener() {
        listener = new MembersAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), AdminMemberProfileActivity.class);
                String uid = members.get(position).getUid();
                intent.putExtra("userId", uid);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                finish();
            }
        };
    }
}