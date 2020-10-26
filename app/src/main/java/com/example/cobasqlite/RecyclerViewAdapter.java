package com.example.cobasqlite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList descList;
    private ArrayList namaList;
    private ArrayList alamatList;
    private Context context;

    RecyclerViewAdapter(ArrayList namaList, ArrayList alamatList, ArrayList descList){
        this.namaList = namaList;
        this.alamatList = alamatList;
        this.descList = descList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Nama, Alamat, Desc;
        private ImageButton more;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            Nama = itemView.findViewById(R.id.name);
            Alamat = itemView.findViewById(R.id.alamatt);
            Desc = itemView.findViewById(R.id.deskripsii);
            more = itemView.findViewById(R.id.overflow);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdesign, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final String Nama = (String) namaList.get(position);
        final String Alamat = (String) alamatList.get(position);
        final String Deskripsi = (String) descList.get(position);
        holder.Nama.setText(Nama);
        holder.Alamat.setText(Alamat);
        holder.Desc.setText(Deskripsi);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                db_profile getDatabase = new db_profile(view.getContext());
                                SQLiteDatabase DeleteData = getDatabase.getWritableDatabase();
                                String selection = db_profile.MyColumns.Nama + " LIKE ?";
                                String[] selectionArgs = {holder.Nama.getText().toString()};
                                DeleteData.delete(db_profile.MyColumns.NamaTabel, selection, selectionArgs);

                                int position = alamatList.indexOf(Alamat);
                                alamatList.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(view.getContext(),"Data Dihapus",Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.update:
                                Intent dataForm = new Intent(view.getContext(), UpdateEvent.class);
                                dataForm.putExtra("SendDesc", holder.Desc.getText().toString());
                                dataForm.putExtra("SendAlamat", holder.Alamat.getText().toString());
                                dataForm.putExtra("SendNama", holder.Nama.getText().toString());
                                context.startActivity(dataForm);
                                ((Activity)context).finish();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return alamatList.size();
    }

}