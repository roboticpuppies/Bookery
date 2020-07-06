package com.example.bookery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DetailBuku extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView judul, register, pengarang, penerbit, tahun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);

        getSupportActionBar().setTitle("Detail Buku");
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(DetailBuku.this, Signin.class));
        }
        judul = findViewById(R.id.tv_detail_judul);
        register = findViewById(R.id.tv_detail_register);
        pengarang = findViewById(R.id.tv_detail_pengarang);
        penerbit = findViewById(R.id.tv_detail_penerbit);
        tahun = findViewById(R.id.tv_detail_tahun);

        final String getJudul = getIntent().getExtras().getString("dataJudul");
        final String getRegister = getIntent().getExtras().getString("dataRegister");
        final String getPengarang = getIntent().getExtras().getString("dataPengarang");
        final String getPenerbit = getIntent().getExtras().getString("dataPenerbit");
        final String getTahun = getIntent().getExtras().getString("dataTahun");
        judul.setText(getJudul);
        register.setText(getRegister);
        pengarang.setText(getPengarang);
        penerbit.setText(getPenerbit);
        tahun.setText(getTahun);
    }
}