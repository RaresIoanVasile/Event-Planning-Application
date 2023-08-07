package acs.upb.licenta.aplicatiegrup.popups;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.Group;

public class PopCreateGroup extends Activity {

    private Button createButton;
    private EditText groupName;
    private TextView text;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uid;

    private String groupCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_create_group);

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

        createButton = findViewById(R.id.create_group_part2);
        groupName = findViewById(R.id.group_name);
        text = findViewById(R.id.choose_name);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createButton.getText().equals("Create group")) {
                    String groupNameStr = groupName.getText().toString().trim();
                    if (groupNameStr.trim().equals("")) {
                        Toast toast = Toast.makeText(PopCreateGroup.this, "The name of the group cannot be empty!", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        groupCode = getAlphaNumericString(15);
                        Group group = new Group(groupNameStr, uid, groupCode, uid);

                        FirebaseDatabase.getInstance().getReference("Groups").child(groupCode).setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PopCreateGroup.this, "The new group was created.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(PopCreateGroup.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        FirebaseDatabase.getInstance().getReference("GroupsCreated").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                int value = snapshot.getValue(Integer.class) + 1;
                                FirebaseDatabase.getInstance().getReference("GroupsCreated").setValue(value);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });

                        text.setText("This is your group code:");
                        groupName.setText(groupCode);
                        createButton.setText("Copy to clipboard");
                    }
                } else {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", groupCode);
                    clipboard.setPrimaryClip(clip);
                }
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