package acs.upb.licenta.aplicatiegrup.groupActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.Date;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Event;
import acs.upb.licenta.aplicatiegrup.classes.Group;
import acs.upb.licenta.aplicatiegrup.classes.Notification;
import acs.upb.licenta.aplicatiegrup.classes.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class MemberProfileActivity extends AppCompatActivity {

    String userId, groupId;
    String uid;
    TextView name, phone, birthday, hobbies, skills;
    ImageButton back;
    CircleImageView imageView;
    Button removeMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        Bundle bundle = getIntent().getExtras();
        userId = "";
        if (bundle != null) {
            userId = bundle.getString("userId");
        }
        groupId = "";
        if (bundle != null) {
            groupId = bundle.getString("groupId");
        }

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        name = findViewById(R.id.profile_fullname2);
        phone = findViewById(R.id.profile_phone2);
        birthday = findViewById(R.id.profile_birthdate2);
        hobbies = findViewById(R.id.profile_hobbies2);
        skills = findViewById(R.id.profile_skills2);
        imageView = findViewById(R.id.imageView_profile2);
        back = findViewById(R.id.backMembers);
        removeMember = findViewById(R.id.remove_member);

        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                name.setText(user.getName());
                phone.setText(user.getPhone());
                birthday.setText(user.getBirthdate());
                if (user.getHobbies() != null) {
                    hobbies.setText(user.getHobbies());
                }
                if (user.getSkills() != null) {
                    skills.setText(user.getSkills());
                }
                if (FirebaseStorage.getInstance().getReference("images/" + userId) != null) {
                    FirebaseStorage.getInstance().getReference().child("images/" + userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).fit().centerCrop().into(imageView);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        removeMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Groups").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Group group = snapshot.getValue(Group.class);

                        if (uid.trim().equals(group.getAdmin().trim())) {
                            String newMembers = "";
                            for (String member : group.getMembers().split(",")) {
                                if (!member.trim().equals("") && !member.trim().equals(userId)) {
                                    newMembers += member.trim() + ",";
                                }
                            }
                            FirebaseDatabase.getInstance().getReference("Groups").child(groupId)
                                    .child("members").setValue(newMembers);

                            FirebaseDatabase.getInstance().getReference("Events").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        Event event = dataSnapshot.getValue(Event.class);
                                        if (event.getGroupId().trim().equals(groupId)) {
                                            String[] notAttending = event.getNotAttending().split(",");
                                            String neww = "";
                                            for (String n : notAttending) {
                                                if (!n.trim().equals("") && !n.trim().equals(userId)) {
                                                    neww += n + ",";
                                                }
                                            }
                                            FirebaseDatabase.getInstance().getReference("Events").child(event.getEventId())
                                                    .child("notAttending").setValue(neww);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });

                            FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);
                                    String message = user.getName() + " was removed from group (" + group.getName() + ")." ;
                                    long time = new Date().getTime();
                                    Notification notification = new Notification(message, groupId, time, "-1", userId);
                                    FirebaseDatabase.getInstance().getReference("Notifications").child(Long.toString(time)).setValue(notification);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });

                            Intent intent = new Intent(getApplicationContext(), ViewMembersActivity.class);
                            intent.putExtra("groupId", groupId);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(MemberProfileActivity.this, "Only the group owner can remove members!", Toast.LENGTH_LONG);
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
                Intent intent = new Intent(getApplicationContext(), ViewMembersActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                finish();
            }
        });

    }
}