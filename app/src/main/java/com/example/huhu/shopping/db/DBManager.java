package com.example.huhu.shopping.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
            mDataBase.execSQL("INSERT INTO product VALUES(null, ?, ?, ?, ?)", new Object[]{infos.getName(), infos.getPrice(), infos.getIntro(), infos.getPicture()});
            mDataBase.setTransactionSuccessful();
        }finally {
            mDataBase.endTransaction();
        }

    }
    public List<ProductInfo> query(){

        List<ProductInfo> info=new ArrayList<ProductInfo>();

        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            ProductInfo person = new ProductInfo();
            person.setName(c.getString(c.getColumnIndex("name")));
            person.setPrice(c.getString(c.getColumnIndex("price")));
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
