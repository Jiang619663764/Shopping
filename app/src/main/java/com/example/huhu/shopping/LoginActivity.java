package com.example.huhu.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huhu.shopping.bean.UserInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtName;
    private EditText mEdtPassword;
    private Button mBtnCommit;
    private Button mBtnRegister;
    private Button mBtnForget;

    private UserInfo userInfo;

    private List<UserInfo> listInfo;

    private String name;

    private String psw;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            listInfo= (List<UserInfo>) msg.obj;
            for (UserInfo infos : listInfo) {
                String n = infos.getName();
                String phone = infos.getPhone();
                String p = infos.getPassWord();
                if (name.equals(n) && psw.equals(p) ||
                        (name.equals(phone) && psw.equals(p))) {

                    userInfo = new UserInfo();
                    userInfo.setName(name);
                    userInfo.setPhone(infos.getPhone());

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mUserInfo", (Serializable) userInfo);
                    intent.putExtra("userInfo", bundle);
                    setResult(20, intent);
                    finish();
                    return;

                } else {
                    Toast.makeText(LoginActivity.this, "输入的用户名或密码不正确",
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        initView();
    }

    private void initView() {
        mEdtName = (EditText) findViewById(R.id.login_phone);
        mEdtPassword = (EditText) findViewById(R.id.login_password);
        mBtnCommit = (Button) findViewById(R.id.login_come);
        mBtnRegister = (Button) findViewById(R.id.login_register);
        mBtnForget = (Button) findViewById(R.id.login_forget);
        mBtnCommit.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mBtnForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_come:
                 name = mEdtName.getText().toString();
                 psw = mEdtPassword.getText().toString();
                 query(name, psw);
                break;
            case R.id.login_register:
                final RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    @Override
                    public void afterEvent(int i, int i1, Object o) {
//                        super.afterEvent(i, i1, o);
                        //解析注册结果
                        if (i1 == SMSSDK.RESULT_COMPLETE) {
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) o;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            submitUserInfo(country, phone);
                            Intent intent = new Intent(LoginActivity.this,
                                    RegisterActivity.class);
                            intent.putExtra("phone", phone);
                            registerPage.startActivity(intent);
                        }
                    }
                });
                registerPage.show(this);
                break;
            case R.id.login_forget:
                final RegisterPage registerPage01 = new RegisterPage();
                registerPage01.setRegisterCallback(new EventHandler() {
                    @Override
                    public void afterEvent(int i, int i1, Object o) {
//                        super.afterEvent(i, i1, o);
                        //解析注册结果
                        if (i1 == SMSSDK.RESULT_COMPLETE) {
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) o;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            submitUserInfo(country, phone);
                            Intent intent = new Intent(LoginActivity.this,
                                    RegisterActivity.class);
                            intent.putExtra("phone", phone);
                            registerPage01.startActivity(intent);
                        }
                    }
                });
                registerPage01.show(this);
                break;
        }
    }

    /**
     * 提交数据到SMSSDK中
     *
     * @param country 国家
     * @param phone   电话
     */
    public void submitUserInfo(String country, String phone) {
        Random r = new Random();
        String uid = Math.abs(r.nextInt()) + "";
        String nickName = "jp";
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
    }

    /**
     * 查询数据
     * @param name
     * @param psw
     */
    public void query(final String name, final String psw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //查询
                BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                query.findObjects(LoginActivity.this, new FindListener<UserInfo>() {
                    @Override
                    public void onSuccess(List<UserInfo> list) {
                        Message msg=new Message();
                        msg.obj=list;
                        mHandler.sendMessage(msg);

                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }
        }).start();
    }
}
