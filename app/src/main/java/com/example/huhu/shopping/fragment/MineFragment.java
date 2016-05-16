package com.example.huhu.shopping.fragment;


import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private LinearLayout mLayout;
    private ImageView mPicture;
    private TextView mUserName;
    private Button mBtnLogin;
    private Button mBtnOrder;
    private Button mBtnSave;
    private Button mBtnAddress;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
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
                startActivity(intent);
                break;
            case R.id.frg_mine_order:
                break;
            case R.id.frg_mine_save:
                break;
            case R.id.frg_mine_address:
                break;
        }
    }
}
