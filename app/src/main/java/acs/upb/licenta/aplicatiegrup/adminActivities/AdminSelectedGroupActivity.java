package acs.upb.licenta.aplicatiegrup.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.groupActivities.MyGroupsActivity;
import acs.upb.licenta.aplicatiegrup.groupActivities.SelectedGroupActivity;
import acs.upb.licenta.aplicatiegrup.groupActivities.ViewMembersActivity;
import acs.upb.licenta.aplicatiegrup.popups.PopDoubleCheckDeleteGroup;

public class AdminSelectedGroupActivity extends AppCompatActivity {

    TextView groupTitle;
    CardView members, deleteGroup;
    ImageButton back;

    String groupId;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_selected_group);

        Bundle bundle = getIntent().getExtras();
        groupId = "";
        if (bundle != null) {
            groupId = bundle.getString("groupId");
        }

        groupTitle = findViewById(R.id.titleGroup);
        members = findViewById(R.id.viewMembers);
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

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminViewMembersActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                finish();
            }
        });

        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Groups").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Group group = snapshot.getValue(Group.class);
                        Intent intent = new Intent(getApplicationContext(), PopDoubleCheckDeleteGroup.class);
                        intent.putExtra("groupId", groupId);
                        startActivity(intent);
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
                startActivity(new Intent(getApplicationContext(), AdminGroupsActivity.class));
                finish();
            }
        });
    }
}