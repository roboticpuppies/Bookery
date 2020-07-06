package com.example.bookery;

import androidx.annotation.NonNull;
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

public class Signin extends AppCompatActivity {
    private static final String TAG = "Main";
    private Button btn_login;
    private TextView link_register;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText login_email, login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        // Binding
        btn_login = findViewById(R.id.btn_login);
        link_register = findViewById(R.id.link_register);
        login_email = findViewById(R.id.et_login_email);
        login_password = findViewById(R.id.et_login_password);
        progressBar = findViewById(R.id.progress_bar);

        auth = FirebaseAuth.getInstance();
        // Jika sudah login langsung ke MainActivity
        if(auth.getCurrentUser() != null) {
            startActivity(new Intent(Signin.this, MainActivity.class));
        }
        progressBar.setVisibility(View.GONE);
        getSupportActionBar().hide();
        // Click Listener button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = login_email.getText().toString();
                String getPassword = login_password.getText().toString();
                signIn(getEmail, getPassword);
            }
        });
        // Click Listener menuju activity register
        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this, Signup.class));
            }
        });
    }

    private void signIn(String getEmail, String getPassword) {
        if (!validateForm()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(getEmail, getPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    startActivity(new Intent(Signin.this, MainActivity.class));
                } else {
                    Toast.makeText(Signin.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    // Validate form
    private boolean validateForm() {
        boolean valid = true;
        String email = login_email.getText().toString();
        String password = login_password.getText().toString();
        if (TextUtils.isEmpty(email)) {
            login_email.setError("Email harus diisi.");
            valid = false;
        } else {
            login_email.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            login_password.setError("Password harus diisi");
            valid = false;
        } else {
            login_password.setError(null);
        }
        return valid;
    }

}