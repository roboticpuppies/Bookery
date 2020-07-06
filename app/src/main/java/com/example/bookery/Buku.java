package com.example.bookery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buku extends AppCompatActivity implements RecyclerviewAdapter.dataListener {
    FloatingActionButton btn_add_buku;
//    Button btn_add_buku;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;

    private DatabaseReference reference;
    private ArrayList<Model> dataBukus;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);

        recyclerView = findViewById(R.id.rv_list_buku);
        btn_add_buku = findViewById(R.id.btn_add_buku);
        progressBar = findViewById(R.id.progress_bar);
        getSupportActionBar().setTitle("Daftar Buku");
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(Buku.this, Signin.class));
        }
        btn_add_buku.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Buku.this, AddBuku.class));
            }
        });
        InitRecyclerView();
    }

    private void InitRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        GetData();
    }

    private void GetData() {
    reference = FirebaseDatabase.getInstance().getReference().child("Admin/Buku");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            dataBukus = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Model buku = snapshot.getValue(Model.class);
                buku.setKey(snapshot.getKey());
                dataBukus.add(buku);
            }
            adapter = new RecyclerviewAdapter(dataBukus, Buku.this);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), "Gagal memuat data", Toast.LENGTH_LONG).show();
            Log.e("MyListDataActivity", databaseError.getDetails() + " " + databaseError.getMessage());
        }
    });
    }

    @Override
    public void onDeleteData(Model model, int position) {
//        String userID = auth.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Admin/Buku");
        final String getKey = model.getKey();
        if(reference != null) {
            reference.child(getKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Buku.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}