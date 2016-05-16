package com.example.huhu.shopping;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MyApplication extends Application {

    private static final String BMOB_APP_KEY="859cc59f2611854ef777d1d786891235";
    private static final String MOB_KEY="10a71b20e0ee8";
    private static final String MOB_SECRET="a8365658e019a4cbf9f90128937d23e2";
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob的SDK
        Bmob.initialize(getApplicationContext(),BMOB_APP_KEY);
        //Fresco初始化
        Fresco.initialize(getApplicationContext());
        //短信验证MOB_SMS初始化
        SMSSDK.initSDK(getApplicationContext(),MOB_KEY,MOB_SECRET);
    }
}
