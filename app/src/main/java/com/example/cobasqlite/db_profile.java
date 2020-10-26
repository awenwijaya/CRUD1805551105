package com.example.cobasqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class  db_profile extends SQLiteOpenHelper {

    static abstract class MyColumns implements BaseColumns {
        static final String NamaTabel = "Profile";
        static final String Nama = "Nama_User";
        static final String TanggalLahir = "Tanggal_Lahir";
        static final String Alamat = "Alamat_User";
    }

    private static final String NamaDatabse = "profile.db";
    private static final int VersiDatabase = 1;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+MyColumns.NamaTabel+
            "("+MyColumns.Nama+" TEXT NOT NULL, "+MyColumns.TanggalLahir+" TEXT NOT NULL, "+MyColumns.Alamat+
            " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+MyColumns.NamaTabel;

    public db_profile(Context context) {
        super(context, NamaDatabse, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

