package com.example.cobasqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lihat_Profile extends AppCompatActivity {

    private ListView listView;
    private db_profile database;
    private ArrayList ListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat__profile);

        getSupportActionBar().setTitle("Profile");
        listView = findViewById(R.id.list);
        ListData = new ArrayList<>();
        database = new db_profile(getBaseContext());
        getData();
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListData));
    }

    @SuppressLint("Recycle")
    private void getData() {
        SQLiteDatabase ReadData = database.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM " + db_profile.MyColumns.NamaTabel, null);
        cursor.moveToFirst();
        for (int count = 0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);
            ListData.add(cursor.getString(0));
            ListData.add(cursor.getString(1));
        }
    }
}
