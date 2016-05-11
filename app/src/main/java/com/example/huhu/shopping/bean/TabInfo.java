package com.example.huhu.shopping.bean;

/**
 * Created by Administrator on 2016/5/11.
 */
public class TabInfo {
    private String title;
    private int picture;
    private Class fragment;

    public TabInfo(String title, int picture, Class fragment) {
        this.title = title;
        this.picture = picture;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
