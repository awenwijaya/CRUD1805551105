package com.example.cobasqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateEvent extends AppCompatActivity {

    private db_profile MyDatabase;
    private EditText NewDesc, NewNama, NewTgl;
    private String getNewDesc, getNewNama, getNewTgl;
    private Button Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        getSupportActionBar().setTitle("Masukan Data Baru");
        MyDatabase = new db_profile(getBaseContext());
        NewDesc = findViewById(R.id.new_deskripsi);
        NewNama = findViewById(R.id.new_nama);
        NewTgl = findViewById(R.id.new_alamat);

        //Menerima Data Nama dan NIM yang telah dipilih Oleh User untuk diposes
        NewNama.setText(getIntent().getExtras().getString("SendNama"));
        NewDesc.setText(getIntent().getExtras().getString("SendDesc"));
        NewTgl.setText(getIntent().getExtras().getString("SendTanggal"));

        Update = findViewById(R.id.new_data);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpdateData();
                startActivity(new Intent(UpdateEvent.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setUpdateData(){
        getNewDesc = NewDesc.getText().toString();
        getNewNama = NewNama.getText().toString();
        getNewTgl = NewTgl.getText().toString();


        SQLiteDatabase database = MyDatabase.getReadableDatabase();

        //Memasukan Data baru pada 3 kolom (NIM, Nama dan Jurusan)
        ContentValues values = new ContentValues();
        values.put(db_profile.MyColumns.Nama, getNewNama);
        values.put(db_profile.MyColumns.Alamat, getNewDesc);
        values.put(db_profile.MyColumns.TanggalLahir, getNewTgl);

        //Untuk Menentukan Data/Item yang ingin diubah, berdasarkan NIM
        String selection = db_profile.MyColumns.Nama + " LIKE ?";
        String[] selectionArgs = {getNewNama};
        database.update(db_profile.MyColumns.NamaTabel, values, selection, selectionArgs);
        Toast.makeText(getApplicationContext(), "Berhasil Diubah", Toast.LENGTH_SHORT).show();
    }
}