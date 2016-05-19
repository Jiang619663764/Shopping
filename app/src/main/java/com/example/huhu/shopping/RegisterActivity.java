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

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEdtName;
    private EditText mEdtPassword;
    private EditText mEdtPasswordRep;
    private Button mBtnCommit;

    private String phoneNum;

    private boolean isReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        initView();
        Intent intent=getIntent();
        phoneNum=intent.getStringExtra("phone");
        findUserIsExit();
    }

    private void findUserIsExit() {

                BmobQuery<UserInfo> query=new BmobQuery<UserInfo>();
                query.findObjects(RegisterActivity.this, new FindListener<UserInfo>() {
                    @Override
                    public void onSuccess(List<UserInfo> list) {

                        for (UserInfo info:list){
                            String phone=info.getPhone();
                            if(phone.equals(phoneNum)){
                                Toast.makeText(RegisterActivity.this, "该用户已存在", Toast.LENGTH_SHORT).show();
                                isReg=true;
                                break;
                            }
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(RegisterActivity.this, "查找失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }


    private void initView() {
        mEdtName= (EditText) findViewById(R.id.register_user);
        mEdtPassword= (EditText) findViewById(R.id.register_password);
        mEdtPasswordRep= (EditText) findViewById(R.id.register_password_rep);
        mBtnCommit= (Button) findViewById(R.id.register_commit);
        mBtnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_commit:
                String name=mEdtName.getText().toString();
                String psw=mEdtPassword.getText().toString();
                String pswRep=mEdtPasswordRep.getText().toString();
                if(psw.equals(pswRep)){
                    if(isReg==false){
                        UserInfo info=new UserInfo();
                        info.setName(name);
                        info.setPassWord(psw);
                        info.setPhone(phoneNum);
                        //保存到BMOB数据库
                        info.save(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
        }
    }
}
