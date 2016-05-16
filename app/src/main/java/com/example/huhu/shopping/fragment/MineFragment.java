package com.example.huhu.shopping.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huhu.shopping.LoginActivity;
import com.example.huhu.shopping.R;
import com.example.huhu.shopping.bean.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private LinearLayout mLayout;
    private ImageView mPicture;
    private TextView mUserName;
    private Button mBtnLogin;
    private Button mBtnOrder;
    private Button mBtnSave;
    private Button mBtnAddress;

    private UserInfo userInfo;

    private static final int REQUEST_CODE=1;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        checkIsLogin();
         return view;
    }

    private void checkIsLogin() {
        sharedPreferences=getActivity().getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin=sharedPreferences.getBoolean("login",false);
        if(isLogin){
            mLayout.setVisibility(View.VISIBLE);
            mBtnLogin.setVisibility(View.GONE);
            mUserName.setText(sharedPreferences.getString("name",""));
        }else{
            mLayout.setVisibility(View.GONE);
            mBtnLogin.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        mLayout= (LinearLayout) view.findViewById(R.id.frg_mine_linear);
        mPicture= (ImageView) view.findViewById(R.id.frg_mine_img);
        mUserName= (TextView) view.findViewById(R.id.frg_mine_name);
        mBtnLogin= (Button) view.findViewById(R.id.frg_mine_login);
        mBtnOrder= (Button) view.findViewById(R.id.frg_mine_order);
        mBtnSave= (Button) view.findViewById(R.id.frg_mine_save);
        mBtnAddress= (Button) view.findViewById(R.id.frg_mine_address);
        mBtnLogin.setOnClickListener(this);
        mBtnOrder.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnAddress.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frg_mine_login:
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.frg_mine_order:
                break;
            case R.id.frg_mine_save:
                break;
            case R.id.frg_mine_address:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE&&resultCode==20){
            mLayout.setVisibility(View.VISIBLE);
            mBtnLogin.setVisibility(View.GONE);
            Bundle bundle=data.getBundleExtra("userInfo");
            userInfo= (UserInfo) bundle.get("mUserInfo");
            mUserName.setText(userInfo.getName());
            saveLoginInfo(getActivity(),true);
        }
    }

    private void saveLoginInfo(Context context,Boolean isLogin){
        sharedPreferences=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean("login",isLogin);
        editor.putString("name", userInfo.getName());
        editor.putString("phone",userInfo.getPhone());
        editor.commit();
    }
}
