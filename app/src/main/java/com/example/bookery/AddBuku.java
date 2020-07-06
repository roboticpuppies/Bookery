package com.example.bookery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBuku extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText judul, register, pengarang, penerbit, tahun;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buku);
        getSupportActionBar().setTitle("Input Data Buku");
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(AddBuku.this, Signin.class));
        }

        judul = findViewById(R.id.et_judul_buku);
        register = findViewById(R.id.et_no_register);
        pengarang = findViewById(R.id.et_pengarang);
        tahun = findViewById(R.id.et_tahun_terbit);
        penerbit = findViewById(R.id.et_penerbit);
        btn_submit = findViewById(R.id.btn_add_buku_submit);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        String getJudul = judul.getText().toString();
        String getRegister = register.getText().toString();
        String getPengarang = pengarang.getText().toString();
        String getPenerbit = penerbit.getText().toString();
        String getTahun = tahun.getText().toString();
        if (TextUtils.isEmpty(getJudul)) {
            judul.setError("Judul  harus diisi.");
            valid = false;
        } else {
            judul.setError(null);
        }

        if (TextUtils.isEmpty(getRegister)) {
            register.setError("Register harus diisi");
            valid = false;
        } else {
            register.setError(null);
        }

        if (TextUtils.isEmpty(getPengarang)) {
            pengarang.setError("Pengarang harus diisi");
            valid = false;
        } else {
            pengarang.setError(null);
        }

        if (TextUtils.isEmpty(getPenerbit)) {
            penerbit.setError("Penerbit harus diisi");
            valid = false;
        } else {
            penerbit.setError(null);
        }

        if (TextUtils.isEmpty(getTahun)) {
            tahun.setError("Tahun terbit harus diisi");
            valid = false;
        } else {
            tahun.setError(null);
        }
        return valid;
    }

    private void saveBook() {
        if (!validateForm()) {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference = database.getReference("Admin");

        String getJudul = judul.getText().toString();
        String getRegister = register.getText().toString();
        String getPengarang = pengarang.getText().toString();
        String getPenerbit = penerbit.getText().toString();
        String getTahun = tahun.getText().toString();

        getReference = database.getReference();
        getReference.child("Admin").child("Buku").push().setValue(new Model(getJudul, getRegister, getPengarang, getPenerbit, getTahun)).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddBuku.this, "Sukses input data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddBuku.this, Buku.class));
            }
        });
    }
}