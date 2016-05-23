package com.example.huhu.shopping.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.huhu.shopping.AddressActivity;
import com.example.huhu.shopping.LoginActivity;
import com.example.huhu.shopping.R;
import com.example.huhu.shopping.bean.UserInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private LinearLayout mLayout;
    private ImageView mPicture;
    private TextView mUserName;
    private Button mBtnLogin;
    private Button mBtnOrder;
    private Button mBtnSave;
    private Button mBtnAddress;
    private Button mBtnBack;

    private UserInfo userInfo;

    private static final int REQUEST_CODE = 1;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        checkIsLogin();
        return view;
    }

    private void checkIsLogin() {
        sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("login", false);
        if (isLogin) {
            mLayout.setVisibility(View.VISIBLE);
            mBtnLogin.setVisibility(View.GONE);
            mBtnBack.setVisibility(View.VISIBLE);
            mUserName.setText(sharedPreferences.getString("name", ""));
        } else {
            mLayout.setVisibility(View.GONE);
            mBtnLogin.setVisibility(View.VISIBLE);
            mBtnBack.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        mLayout = (LinearLayout) view.findViewById(R.id.frg_mine_linear);
        mPicture = (ImageView) view.findViewById(R.id.frg_mine_img);
        mUserName = (TextView) view.findViewById(R.id.frg_mine_name);
        mBtnLogin = (Button) view.findViewById(R.id.frg_mine_login);
        mBtnOrder = (Button) view.findViewById(R.id.frg_mine_order);
        mBtnSave = (Button) view.findViewById(R.id.frg_mine_save);
        mBtnAddress = (Button) view.findViewById(R.id.frg_mine_address);
        mBtnBack= (Button) view.findViewById(R.id.frg_mine_back);
        mBtnLogin.setOnClickListener(this);
        mBtnOrder.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnAddress.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_mine_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.frg_mine_order:
                sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                boolean isLogin02 = sharedPreferences.getBoolean("login", false);
                if(isLogin02){

                }else{
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.frg_mine_save:
                break;
            case R.id.frg_mine_address:
                sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                boolean isLogin04 = sharedPreferences.getBoolean("login", false);
                if(isLogin04){
                    Intent intent04=new Intent(getActivity(), AddressActivity.class);
                    startActivity(intent04);
                }else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.frg_mine_back:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("确定退出登录？");
                alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putBoolean("login", false);
                        editor.commit();
                        mLayout.setVisibility(View.GONE);
                        mBtnLogin.setVisibility(View.VISIBLE);
                        mBtnBack.setVisibility(View.GONE);
                    }
                });
                alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == 20) {
            mLayout.setVisibility(View.VISIBLE);
            mBtnLogin.setVisibility(View.GONE);
            Bundle bundle = data.getBundleExtra("userInfo");
            userInfo = (UserInfo) bundle.get("mUserInfo");
            mUserName.setText(userInfo.getName());
            saveLoginInfo(getActivity(), true);
        }
    }

    private void saveLoginInfo(Context context, Boolean isLogin) {
        sharedPreferences = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("login", isLogin);
        editor.putString("name", userInfo.getName());
        editor.putString("phone", userInfo.getPhone());
        editor.commit();
    }


}
