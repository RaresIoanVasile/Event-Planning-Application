package acs.upb.licenta.aplicatiegrup.userRelated;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.User;
import acs.upb.licenta.aplicatiegrup.databinding.ActivityProfileBinding;
import acs.upb.licenta.aplicatiegrup.others.DrawerBaseActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends DrawerBaseActivity {

    ActivityProfileBinding activityProfileBinding;
    private EditText textViewWelcome, textViewFullName, textViewEmail,
    textViewPhone, textViewBirthDate, textViewSkills, textViewHobbies;
    private String fullName, email, phone,birthDate, skills, hobbies;
    private CircleImageView imageView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    private Uri image;
    private StorageReference storageReference;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        allocateActivityTitle("Profile");

        imageView = findViewById(R.id.imageView_profile);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewFullName = findViewById(R.id.profile_fullname);
        textViewEmail = findViewById(R.id.profile_email);
        textViewPhone = findViewById(R.id.profile_phone);
        textViewBirthDate = findViewById(R.id.profile_birthdate);
        textViewSkills = findViewById(R.id.profile_skills);
        textViewHobbies = findViewById(R.id.profile_hobbies);
        editButton = findViewById(R.id.edit_profile_button);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");


        if (firebaseUser == null) {
            Toast.makeText(ProfileActivity.this, "No User", Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile(firebaseUser);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 100);

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editButton.getText().equals("Edit Profile")) {
                    textViewFullName.setEnabled(true);
                    textViewPhone.setEnabled(true);
                    textViewHobbies.setEnabled(true);
                    textViewSkills.setEnabled(true);
                    if (hobbies == null) {
                        textViewHobbies.setText(" ");
                        textViewHobbies.setHint("Write your hobbies here.");
                    }
                    if (skills == null) {
                        textViewSkills.setText(" ");
                        textViewSkills.setHint("Write your skills here.");
                    }
                    editButton.setText("Save Changes");
                    return;
                }
                if (editButton.getText().equals("Save Changes")) {
                    databaseReference.child(firebaseUser.getUid()).child("name").setValue(String.valueOf(textViewFullName.getText()));
                    databaseReference.child(firebaseUser.getUid()).child("phone").setValue(String.valueOf(textViewPhone.getText()));
                    databaseReference.child(firebaseUser.getUid()).child("hobbies").setValue(String.valueOf(textViewHobbies.getText()));
                    databaseReference.child(firebaseUser.getUid()).child("skills").setValue(String.valueOf(textViewSkills.getText()));
                    editButton.setText("Edit Profile");
                    textViewFullName.setEnabled(false);
                    textViewPhone.setEnabled(false);
                    textViewHobbies.setEnabled(false);
                    textViewSkills.setEnabled(false);
                    showUserProfile(firebaseUser);
                    return;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            image = data.getData();
            imageView.setImageURI(image);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String fileName = firebaseUser.getUid();
        storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);

        storageReference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    fullName = user.getName();
                    email = firebaseUser.getEmail();
                    birthDate = user.getBirthdate();
                    phone = user.getPhone();
                    hobbies = user.getHobbies();
                    skills = user.getSkills();
                    if (FirebaseStorage.getInstance().getReference("images/" + userID) != null) {
                        FirebaseStorage.getInstance().getReference().child("images/" + userID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).fit().centerCrop().into(imageView);
                            }
                        });
                    }

                    textViewWelcome.setText("Welcome, " + fullName + "!");
                    textViewFullName.setText(fullName);
                    textViewEmail.setText(email);
                    textViewPhone.setText(phone);
                    textViewBirthDate.setText(birthDate);
                    if (image != null) {
                        imageView.setImageURI(image);
                    }

                    if (hobbies == null) {
                        textViewHobbies.setText("What are your hobbies?");
                    } else {
                        textViewHobbies.setText(hobbies);
                    }

                    if (skills == null) {
                        textViewSkills.setText("What are your skills?");
                    } else {
                        textViewSkills.setText(skills);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}