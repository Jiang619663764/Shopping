package com.example.huhu.shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huhu.shopping.bean.ProductInfo;
import com.example.huhu.shopping.db.DBManager;
import com.example.huhu.shopping.fragment.HomeFragment;
import com.example.huhu.shopping.view.MyToolBar;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    private MyToolBar toolBar;
    private SimpleDraweeView mImgPic;
    private TextView mTxtName;
    private TextView mTxtIntro;
    private TextView mTxtPrice;

    private List<ProductInfo> info;

    private int position;

    private Button mBtnAdd;
    private Button mBtnBuy;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        initView();

        Intent intent=getIntent();
        position=intent.getIntExtra("position",0);
        info= (List<ProductInfo>) intent.getSerializableExtra("data");

        getDetailData(position);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnAdd.setOnClickListener(this);
        mBtnBuy.setOnClickListener(this);


    }

    /**
     * 初始化控件
     */
    private void initView() {
        toolBar= (MyToolBar) findViewById(R.id.toolbar);
        mImgPic= (SimpleDraweeView) findViewById(R.id.detail_img);
        mTxtName= (TextView) findViewById(R.id.detail_txt_name);
        mTxtIntro= (TextView) findViewById(R.id.detail_txt_intro);
        mTxtPrice= (TextView) findViewById(R.id.detail_txt_price);
        mBtnAdd= (Button) findViewById(R.id.detail_btn_add);
        mBtnBuy= (Button) findViewById(R.id.detail_btn_buy);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_btn_add:
                DBManager manager=new DBManager(DetailActivity.this);
                ProductInfo proInfo=info.get(position);
                manager.add(proInfo);
                Toast.makeText(DetailActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.detail_btn_buy:
                sharedPreferences = this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                boolean isLogin = sharedPreferences.getBoolean("login", false);
                if(isLogin){
                    Intent intent=new Intent(DetailActivity.this,PayActivity.class);
                    intent.putExtra("OrderPosition",position);
                    intent.putExtra("OrderInfo",(Serializable)info);
                    startActivity(intent);
                }else{
                    Toast.makeText(DetailActivity.this,"请先登录",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    public void getDetailData(int position) {
        mTxtName.setText("商品："+info.get(position).getName());
        mTxtIntro.setText("" + info.get(position).getIntro());
        mTxtPrice.setText("￥" + info.get(position).getPrice());
        Uri uri=Uri.parse(info.get(position).getPicture());
        mImgPic.setImageURI(uri);
    }
}
