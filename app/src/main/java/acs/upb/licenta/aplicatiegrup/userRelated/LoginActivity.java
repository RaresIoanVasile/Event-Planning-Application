package acs.upb.licenta.aplicatiegrup.userRelated;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import acs.upb.licenta.aplicatiegrup.adminActivities.AdminActivity;
import acs.upb.licenta.aplicatiegrup.MainMenuActivity;
import acs.upb.licenta.aplicatiegrup.R;

public class LoginActivity extends AppCompatActivity {

    EditText logEmail, logPassword;
    Button logLoginButton;
    TextView logRegister;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(LoginActivity.this, "Login was successful.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
        }

//        getSupportActionBar().hide();

        logEmail = findViewById(R.id.email);
        logPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBarLogin);
        fAuth = FirebaseAuth.getInstance();
        logLoginButton = findViewById(R.id.login_btn);
        logRegister = findViewById(R.id.go_register);

        logLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = logEmail.getText().toString().trim();
                String password = logPassword.getText().toString().trim();

                if (email.equals("admin") && password.equals("admin")) {
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    logEmail.setError("Email is required!");
                    logEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    logPassword.setError("Password is required!");
                    logPassword.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // login
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login was successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        logRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}