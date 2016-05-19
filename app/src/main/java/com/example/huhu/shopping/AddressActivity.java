package com.example.huhu.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.huhu.shopping.adapter.AddressAdapter;
import com.example.huhu.shopping.bean.AddressInfo;
import com.example.huhu.shopping.db.DBManager;

import java.io.Serializable;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private List<AddressInfo> mInfo;
    private ListView mListView;
    private AddressAdapter mAdapter;
    private Button mBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_address);
        initData();
        mListView = (ListView) findViewById(R.id.address_list);
        mBtnAdd = (Button) findViewById(R.id.address_add);
        mAdapter = new AddressAdapter(AddressActivity.this, mInfo);
        mAdapter.setOnDeleteListener(this);
//        mAdapter.setOnEditListener(this);
        mListView.setAdapter(mAdapter);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        mAdapter = new AddressAdapter(AddressActivity.this, mInfo);
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        DBManager db = new DBManager(this);
        mInfo = db.queryAddress();
        Log.e("AddressActivity", "=-----------mInfo.size()=" + mInfo.size());
    }

    @Override
    public void onClick(View v) {
        final Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.item_address_edit:
                DBManager db01 = new DBManager(this);
                if (tag != null && tag instanceof Integer) {//解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    int position = (Integer) tag;
                    AddressInfo info = db01.queryAddress().get(position + 1);
                    Intent intent = new Intent(AddressActivity.this, EditActivity.class);
                    intent.putExtra("info", (Serializable) info);
                    startActivity(intent);
                }
                break;
            case R.id.item_address_delete:
                AlertDialog.Builder builder=new AlertDialog.Builder(AddressActivity.this);
                builder.setTitle("确定删除此地址");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager db = new DBManager(AddressActivity.this);
                        if (tag != null && tag instanceof Integer) {
                            int position = (Integer) tag;
                            db.deleteAddress(mInfo.get(position).getAddress().toString().trim());
                            mInfo.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();

                break;
        }
    }
}
