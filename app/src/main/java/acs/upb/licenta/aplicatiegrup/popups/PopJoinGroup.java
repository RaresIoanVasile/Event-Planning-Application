package acs.upb.licenta.aplicatiegrup.popups;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class PopJoinGroup extends AppCompatActivity {

    private Button joinButton;
    private EditText groupCode;
    private TextView text;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_join_group);

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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Groups");
        uid = firebaseAuth.getCurrentUser().getUid();

        joinButton = findViewById(R.id.join_group_part2);
        groupCode = findViewById(R.id.group_code);
        text = findViewById(R.id.enter_code);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String id = dataSnapshot.getKey();
                            if (id.equals(String.valueOf(groupCode.getText()).trim())) {
                                Group group = dataSnapshot.getValue(Group.class);
                                String membersStr = group.getMembers();
                                String[] members = membersStr.split(",");
                                boolean isPart = false;
                                for (String member : members) {
                                    if (member.equals(uid)) {
                                        isPart = true;
                                        break;
                                    }
                                }
                                if (!isPart) {
                                    membersStr += "," + uid;
                                    databaseReference.child(id).child("members").setValue(membersStr);

                                    FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            User user = snapshot.getValue(User.class);
                                            String message = user.getName() + " joined group (" + group.getName() + ").";
                                            long time = new Date().getTime();
                                            Notification notification = new Notification(message, group.getCode(), time, "-1", uid);
                                            FirebaseDatabase.getInstance().getReference("Notifications").child(Long.toString(time)).setValue(notification);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {

                                        }
                                    });

                                    groupCode.setText(" ");
                                } else {
                                    Toast.makeText(getApplicationContext(), "You are already in this group!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });
    }
}