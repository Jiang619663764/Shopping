package com.example.huhu.shopping.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/5/11.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "info.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS product" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price FLOAT, info TEXT,image TEXT,count INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS address" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT, detail TEXT,isDefault INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
