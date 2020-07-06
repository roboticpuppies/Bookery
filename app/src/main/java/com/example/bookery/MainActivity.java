package com.example.bookery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ImageView icon_buku;
    private FirebaseAuth auth;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icon_buku = findViewById(R.id.icon_book);
        btn_logout = findViewById(R.id.btn_logout);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, Signin.class));
        }
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Signout berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Signin.class));
            }
        });
        icon_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Buku.class));
            }
        });

    }
}