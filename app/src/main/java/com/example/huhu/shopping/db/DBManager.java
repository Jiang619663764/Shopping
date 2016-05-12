package com.example.huhu.shopping.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huhu.shopping.bean.CartInfo;
import com.example.huhu.shopping.bean.ProductInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class DBManager {
    private DBHelper mHelper;
    private SQLiteDatabase mDataBase;

    public DBManager(Context context){

        mHelper=new DBHelper(context);
        mDataBase=mHelper.getWritableDatabase();
    }

    public void add(ProductInfo infos){
        mDataBase.beginTransaction();

        try{
            mDataBase.execSQL("INSERT INTO product VALUES(null, ?, ?, ?, ?)", new Object[]{infos.getName(), Float.parseFloat(infos.getPrice()), infos.getIntro(), infos.getPicture()});
            mDataBase.setTransactionSuccessful();
        }finally {
            mDataBase.endTransaction();
        }

    }
    public List<CartInfo> query(){

        List<CartInfo> info=new ArrayList<CartInfo>();

        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            CartInfo person = new CartInfo();
            person.setName(c.getString(c.getColumnIndex("name")));
            person.setPrice(c.getFloat(c.getColumnIndex("price")));
            person.setIntro(c.getString(c.getColumnIndex("info")));
            person.setPicture(c.getString(c.getColumnIndex("image")));
            info.add(person);
        }
        c.close();
        return info;
    }

    public Cursor queryTheCursor() {
        Cursor c = mDataBase.rawQuery("SELECT * FROM product", null);
        return c;
    }
}
