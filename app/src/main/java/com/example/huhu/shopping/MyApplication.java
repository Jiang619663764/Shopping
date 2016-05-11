package com.example.huhu.shopping;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MyApplication extends Application {

    private static final String BMOB_APP_KEY="859cc59f2611854ef777d1d786891235";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob的SDK
        Bmob.initialize(getApplicationContext(),BMOB_APP_KEY);
    }
}
