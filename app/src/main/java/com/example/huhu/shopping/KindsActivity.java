package com.example.huhu.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huhu.shopping.R;
import com.example.huhu.shopping.adapter.ProductAdapter;
import com.example.huhu.shopping.bean.ProductInfo;
import com.example.huhu.shopping.view.MyToolBar;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class KindsActivity extends AppCompatActivity {

    private String type;
    private List<ProductInfo> kindsInfo;
    private ListView mListView;
    private ProductAdapter mAdapter;
    private MyToolBar toolBar;

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_kinds);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        getDataByType(this);
        initView();
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                kindsInfo= (List<ProductInfo>) msg.obj;
                mAdapter=new ProductAdapter(KindsActivity.this,kindsInfo);
                mListView.setAdapter(mAdapter);
            }
        };

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(KindsActivity.this,"点击的选项："+position,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(KindsActivity.this,DetailActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("data", (Serializable) kindsInfo);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        mListView= (ListView) findViewById(R.id.kinds_listview);
        toolBar= (MyToolBar) findViewById(R.id.kinds_toolbar);
        toolBar.setTitle(type);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataByType(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<ProductInfo> query=new BmobQuery<ProductInfo>();
                query.addWhereEqualTo("type",type);
                query.findObjects(context, new FindListener<ProductInfo>() {
                    @Override
                    public void onSuccess(List<ProductInfo> list) {
                        Message msg=new Message();
                        msg.obj=list;
                        mHandler.sendMessage(msg);
                        Log.e("KindsActivity","------------------------"+list.size());
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("KindsActivity","----------Error--------------"+s);
                    }
                });
            }
        }).start();
    }

}
