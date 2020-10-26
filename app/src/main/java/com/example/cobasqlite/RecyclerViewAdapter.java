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

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList namaList; //Digunakan untuk Nama
    private ArrayList alamatList; //Digunakan untuk Jurusan
    private ArrayList tglList; //Digunakan untuk Jurusan
    private Context context;

    //Membuat Konstruktor pada Class RecyclerViewAdapter
    RecyclerViewAdapter(ArrayList namaList, ArrayList alamatList, ArrayList tglList){
        this.namaList = namaList;
        this.alamatList = alamatList;
        this.tglList = tglList;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Nama, Alamat, Tgl;
        private ImageButton more;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
            Nama = itemView.findViewById(R.id.name);
            Alamat = itemView.findViewById(R.id.alamatt);
            Tgl = itemView.findViewById(R.id.deskripsii);
            more = itemView.findViewById(R.id.overflow);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdesign, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Memanggil Nilai/Value Pada View-View Yang Telah Dibuat pada Posisi Tertentu
        final String Nama = (String) namaList.get(position);//Mengambil data (Nama) sesuai dengan posisi yang telah ditentukan
        final String Alamat = (String) alamatList.get(position);//Mengambil data (Jurusan) sesuai dengan posisi yang telah ditentukan
        final String Tanggal = (String) tglList.get(position);//Mengambil data (NIM) sesuai dengan posisi yang telah ditentukan
        holder.Nama.setText(Nama);
        holder.Alamat.setText(Alamat);
        holder.Tgl.setText(Tanggal);

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
                                //Menghapus Data Dari Database
                                db_profile getDatabase = new db_profile(view.getContext());
                                SQLiteDatabase DeleteData = getDatabase.getWritableDatabase();
                                //Menentukan di mana bagian kueri yang akan dipilih
                                String selection = db_profile.MyColumns.Nama + " LIKE ?";
                                //Menentukan Nama Dari Data Yang Ingin Dihapus
                                String[] selectionArgs = {holder.Nama.getText().toString()};
                                DeleteData.delete(db_profile.MyColumns.NamaTabel, selection, selectionArgs);

                                //Menghapus Data pada List dari Posisi Tertentu
                                int position = alamatList.indexOf(Alamat);
                                alamatList.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(view.getContext(),"Data Dihapus",Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.update:
                                Intent dataForm = new Intent(view.getContext(), UpdateEvent.class);
                                dataForm.putExtra("SendTanggal", holder.Tgl.getText().toString());
                                dataForm.putExtra("SendDesc", holder.Alamat.getText().toString());
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
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return alamatList.size();
    }

}