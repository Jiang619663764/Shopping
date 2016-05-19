package com.example.huhu.shopping.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huhu.shopping.bean.AddressInfo;
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

    /**
     * 添加产品信息
     * @param infos
     */
    public void add(ProductInfo infos){
        mDataBase.beginTransaction();

        try{
            mDataBase.execSQL("INSERT INTO product VALUES(null, ?, ?, ?, ?)", new Object[]{infos.getName(), Float.parseFloat(infos.getPrice()), infos.getIntro(), infos.getPicture()});
            mDataBase.setTransactionSuccessful();
        }finally {
            mDataBase.endTransaction();
        }

    }
    /**
     * 添加地址信息
     * @param infos
     */
    public void addAddress(AddressInfo infos){
        mDataBase.beginTransaction();

        try{
            mDataBase.execSQL("INSERT INTO address VALUES(null, ?, ?, ?, ?)", new Object[]{infos.getName(), infos.getPhone(), infos.getAddress(), infos.isDefault()});
            mDataBase.setTransactionSuccessful();
        }finally {
            mDataBase.endTransaction();
        }
    }
    /**
     * 查询产品信息
     * @return
     */
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
    /**
     * 查询地址信息
     * @return
     */
    public List<AddressInfo> queryAddress(){

        List<AddressInfo> info=new ArrayList<AddressInfo>();

        Cursor c = queryAddressTheCursor();
        while (c.moveToNext()) {
            AddressInfo person = new AddressInfo();
            person.setName(c.getString(c.getColumnIndex("name")));
            person.setPhone(c.getString(c.getColumnIndex("phone")));
            person.setAddress(c.getString(c.getColumnIndex("detail")));
            person.setIsDefault(c.getInt(c.getColumnIndex("isDefault")));
            info.add(person);
        }
        c.close();
        return info;
    }

    public Cursor queryTheCursor() {
        Cursor c = mDataBase.rawQuery("SELECT * FROM product", null);
        return c;
    }

    public Cursor queryAddressTheCursor() {
        Cursor c = mDataBase.rawQuery("SELECT * FROM address", null);
        return c;
    }
    /**
     * 删除商品信息
     * @param intro
     */
    public void delete(String intro){

        mDataBase.delete("product","_id=?",new String[]{queryId(intro)+""});
    }
    /**
     *  根据地址详情删除信息
     */
    public void deleteAddress(String  address){

        mDataBase.delete("address", "_id=?", new String[]{queryAddressId(address) + ""});

    }
    /**
     * 根据地址详情获取地址id
     * @param address
     * @return
     */
    public int queryAddressId(String address){
        int i = 0;
        String sql="SELECT * FROM address WHERE detail="+"'"+address+"'";
        Cursor cursor=mDataBase.rawQuery(sql, null);
        while (cursor.moveToNext()){
             i= cursor.getInt(cursor.getColumnIndex("_id"));
        }
        cursor.close();
        return i;
    }
    /**
     * 根据地址详情获取地址id
     * @param intro
     * @return
     */
    public int queryId(String intro){
        int i = 0;
        String sql="SELECT * FROM product WHERE info="+"'"+intro+"'";
        Cursor cursor=mDataBase.rawQuery(sql, null);
        while (cursor.moveToNext()){
            i= cursor.getInt(cursor.getColumnIndex("_id"));
        }
        cursor.close();
        return i;
    }
}
