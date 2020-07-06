package com.example.bookery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.util.GAuthToken;

public class EditBuku extends AppCompatActivity {
    private EditText judul, register, pengarang, penerbit, tahun;
    private Button btn_submit;
    private DatabaseReference database;
    private String cekJudul, cekRegister, cekPengarang, cekTahun, cekPenerbit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_buku);

        getSupportActionBar().setTitle("Update Buku");

        judul = findViewById(R.id.et_edit_judul_buku);
        register = findViewById(R.id.et_edit_no_register);
        pengarang = findViewById(R.id.et_edit_pengarang);
        tahun = findViewById(R.id.et_edit_tahun_terbit);
        penerbit = findViewById(R.id.et_edit_penerbit);
        btn_submit = findViewById(R.id.btn_edit_buku_submit);

        database = FirebaseDatabase.getInstance().getReference();
        getData();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekJudul = judul.getText().toString();
                cekRegister = register.getText().toString();
                cekPenerbit = penerbit.getText().toString();
                cekPengarang = pengarang.getText().toString();
                cekTahun = tahun.getText().toString();
                Model setBuku = new Model(cekJudul, cekRegister, cekPengarang, cekPenerbit, cekTahun);
                updateBuku(setBuku);
            }

            private void updateBuku(Model setBuku) {
                if (!validateForm()) {
                    return;
                }
                String getKey = getIntent().getExtras().getString("getPrimaryKey");
                database.child("Admin/Buku").child(getKey).setValue(setBuku).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditBuku.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
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

    private void getData() {
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