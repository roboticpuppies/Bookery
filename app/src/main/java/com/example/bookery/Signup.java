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

public class Signup extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText login_email, login_password;
    private Button btn_signup;
    private TextView link_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login_email = findViewById(R.id.et_login_email);
        login_password = findViewById(R.id.et_login_password);
        progressBar = findViewById(R.id.progress_bar);
        btn_signup = findViewById(R.id.btn_signup);
        link_login = findViewById(R.id.link_login);

        auth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.GONE);
        getSupportActionBar().hide();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = login_email.getText().toString();
                String getPassword = login_password.getText().toString();
                signUp(getEmail, getPassword);
            }
        });
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, Signin.class));
            }
        });
    }
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

    private void signUp(String getEmail, String getPassword) {
        if (!validateForm()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(getEmail, getPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(Signup.this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Signup.this, "Gagal daftar", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}