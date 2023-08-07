package acs.upb.licenta.aplicatiegrup.userRelated;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.classes.User;

public class RegisterActivity extends AppCompatActivity {

    EditText regEmail, regFullname, regPhone, regPassword, regBirthdate;
    Button regRegisterButton;
    TextView regLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    final Calendar calendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail = findViewById(R.id.email);
        regFullname = findViewById(R.id.fullname);
        regPhone = findViewById(R.id.phone);
        regPassword = findViewById(R.id.password);
        regBirthdate = findViewById(R.id.birthdate);
        regRegisterButton = findViewById(R.id.register_btn);
        regLogin = findViewById(R.id.go_login);
        progressBar = findViewById(R.id.progressBarRegister);
        fAuth = FirebaseAuth.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        regBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        regRegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = regEmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String fullName = regFullname.getText().toString().trim();
                String phone = regPhone.getText().toString().trim();
                String birthdate = regBirthdate.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    regEmail.setError("Email is required!");
                    regEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    regPassword.setError("Password is required!");
                    regPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(fullName)) {
                    regFullname.setError("Full name is required!");
                    regFullname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    regPhone.setError("Phone number is required!");
                    regPhone.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    regPassword.setError("Password must be more than 6 characters long!");
                    regPassword.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    regEmail.setError("Enter a valid email!");
                    return;
                }
                if (TextUtils.isEmpty(fullName)) {
                    regFullname.setError("Full name is required!");
                    regFullname.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String[] fields = birthdate.split("/");
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String today = df.format(c);
                            String[] fieldsToday = today.split("/");
                            String kid = "0";
                            if (Integer.parseInt(fieldsToday[2]) - Integer.parseInt(fields[2]) < 14) {
                                kid = "1";
                            }
                            User user = new User(email, fullName, phone, birthdate, FirebaseAuth.getInstance().getCurrentUser().getUid(), kid);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Register was successful.", Toast.LENGTH_SHORT).show();
                                        FirebaseDatabase.getInstance().getReference("UsersCreated").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                int value = snapshot.getValue(Integer.class) + 1;
                                                FirebaseDatabase.getInstance().getReference("UsersCreated").setValue(value);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {

                                            }
                                        });
                                    }
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        regBirthdate.setText(dateFormat.format(calendar.getTime()));
    }
}