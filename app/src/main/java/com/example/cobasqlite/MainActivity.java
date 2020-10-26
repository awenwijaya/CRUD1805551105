package com.example.cobasqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nama, alamat, tgl;
    private Button regis, liat;
    private String setnama, setalamat, settgl;
    private db_profile db_profile1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        tgl = findViewById(R.id.tanggal);
        regis = findViewById(R.id.regis1);
        liat = findViewById(R.id.liatdata);

        db_profile1 = new db_profile(getBaseContext());

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                saveData();
                Toast.makeText(getApplicationContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                cleardata();
            }
        });

        liat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lihatvolunt.class);
                startActivity(intent);
            }
        });
    }

    private void setData(){
        setnama = nama.getText().toString();
        setalamat = alamat.getText().toString();
        settgl = tgl.getText().toString();
    }

    private void saveData(){
        SQLiteDatabase create = db_profile1.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(com.example.cobasqlite.db_profile.MyColumns.Nama, setnama);
        values.put(com.example.cobasqlite.db_profile.MyColumns.Alamat, setalamat);
        values.put(com.example.cobasqlite.db_profile.MyColumns.TanggalLahir, settgl);

        create.insert(com.example.cobasqlite.db_profile.MyColumns.NamaTabel, null, values);
    }

    private void cleardata(){
        nama.setText("");
        alamat.setText("");
        tgl.setText("");
    }

}
