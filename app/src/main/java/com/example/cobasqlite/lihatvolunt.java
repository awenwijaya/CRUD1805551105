package com.example.cobasqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class lihatvolunt extends AppCompatActivity {

    private db_profile Mydatabase;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList NamaList;
    private ArrayList AlamatList;
    private ArrayList TanggalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihatvolunt);

        getSupportActionBar().setTitle("List ACARA VOLUNTEER");
        NamaList = new ArrayList<>();
        AlamatList = new ArrayList<>();
        TanggalList = new ArrayList<>();

        Mydatabase = new db_profile(getBaseContext());
        recyclerView = findViewById(R.id.review);
        getData();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(NamaList, AlamatList, TanggalList);
        recyclerView.setAdapter(adapter);

    }

    @SuppressLint("Recycle")
    protected void getData(){
        SQLiteDatabase ReadData = Mydatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM "+ db_profile.MyColumns.NamaTabel, null);

        cursor.moveToFirst();
        for (int count=0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);
            NamaList.add(cursor.getString(0));
            AlamatList.add(cursor.getString(1));
            TanggalList.add(cursor.getString(2));
        }
    }

}
