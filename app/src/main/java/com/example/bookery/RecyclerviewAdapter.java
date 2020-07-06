package com.example.bookery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private ArrayList<Model> listBuku;
    private Context context;
    dataListener listener;
    public RecyclerviewAdapter(ArrayList<Model> listBuku, Context context) {
        this.listBuku = listBuku;
        this.context = context;
        listener = (Buku)context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String Judul = listBuku.get(position).getJudul();
        final String Pengarang = listBuku.get(position).getPengarang();

        holder.Judul.setText(Judul);
        holder.Pengarang.setText(Pengarang);
        holder.ListItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle bundle = new Bundle();
                bundle.putString("getPrimaryKey", listBuku.get(position).getKey());
                bundle.putString("dataJudul", listBuku.get(position).getJudul());
                bundle.putString("dataRegister", listBuku.get(position).getRegister());
                bundle.putString("dataPengarang", listBuku.get(position).getPengarang());
                bundle.putString("dataPenerbit", listBuku.get(position).getPenerbit());
                bundle.putString("dataTahun", listBuku.get(position).getTahun());
                Intent intent = new Intent(v.getContext(), DetailBuku.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(final View v) {
                final String[] action = {"Edit", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Bundle bundle = new Bundle();
                                bundle.putString("getPrimaryKey", listBuku.get(position).getKey());
                                bundle.putString("dataJudul", listBuku.get(position).getJudul());
                                bundle.putString("dataRegister", listBuku.get(position).getRegister());
                                bundle.putString("dataPengarang", listBuku.get(position).getPengarang());
                                bundle.putString("dataPenerbit", listBuku.get(position).getPenerbit());
                                bundle.putString("dataTahun", listBuku.get(position).getTahun());
                                Intent intent = new Intent(v.getContext(), EditBuku.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                listener.onDeleteData(listBuku.get(position), position);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBuku.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Judul, Pengarang;
        private LinearLayout ListItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Judul = itemView.findViewById(R.id.tv_list_judul);
            Pengarang = itemView.findViewById(R.id.tv_list_pengarang);
            ListItem = itemView.findViewById(R.id.view_design_listdata);
        }
    }

    public interface dataListener{
        void onDeleteData(Model model, int position);
    }
}
