package acs.upb.licenta.aplicatiegrup.groupActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.eventActivities.MyEventsActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.SelectedEventActivity;
import acs.upb.licenta.aplicatiegrup.popups.PopDoubleCheckDeleteGroup;
import acs.upb.licenta.aplicatiegrup.popups.PopDoubleCheckLeaveGroup;
import acs.upb.licenta.aplicatiegrup.popups.PopGroupCode;

public class SelectedGroupActivity extends AppCompatActivity {

    TextView groupTitle;
    CardView members, viewGroupCode, leaveGroup, deleteGroup;
    ImageButton back;

    String groupId;
    DatabaseReference databaseReference;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_group);

        Bundle bundle = getIntent().getExtras();
        groupId = "";
        if (bundle != null) {
            groupId = bundle.getString("groupId");
        }

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        groupTitle = findViewById(R.id.titleGroup);
        members = findViewById(R.id.viewMembers);
        viewGroupCode = findViewById(R.id.viewGroupCode);
        leaveGroup = findViewById(R.id.leaveGroup);
        deleteGroup = findViewById(R.id.deleteGroup);
        back = findViewById(R.id.backSelectedGroup);

        databaseReference = FirebaseDatabase.getInstance().getReference("Groups").child(groupId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Group group = snapshot.getValue(Group.class);
                groupTitle.setText(group.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        viewGroupCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopGroupCode.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewMembersActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                finish();
            }
        });

        leaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopDoubleCheckLeaveGroup.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }

        });

        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Groups").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Group group = snapshot.getValue(Group.class);
                        if (group.getAdmin().trim().equals(uid)) {
                            Intent intent = new Intent(getApplicationContext(), PopDoubleCheckDeleteGroup.class);
                            intent.putExtra("groupId", groupId);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(SelectedGroupActivity.this, "Only the group's owner can delete this group!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyGroupsActivity.class));
                finish();
            }
        });
    }
}