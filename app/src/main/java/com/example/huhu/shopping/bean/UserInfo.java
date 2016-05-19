package com.example.huhu.shopping.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by lenovo on 2016/5/16.
 */
public class UserInfo extends BmobObject implements Serializable{

    private String name;
    private String phone;
    private String passWord;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
