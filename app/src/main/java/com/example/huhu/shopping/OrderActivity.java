package com.example.huhu.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huhu.shopping.bean.AddressInfo;
import com.example.huhu.shopping.bean.CartInfo;
import com.example.huhu.shopping.bean.OrderInfo;
import com.example.huhu.shopping.db.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderActivity extends AppCompatActivity {

    private TextView name;
    private TextView phone;
    private TextView address;
    private TextView orderInfo;
    private TextView price;
    private Button commit;
    private RadioGroup radioGroup;
    private RadioButton onLine, outLine;

    private float sum;
    private int isPay = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_order);
        Intent intent = getIntent();
        sum = intent.getFloatExtra("price", 0);
        initView();
        getProInfo();
        getAddressInfo(0);
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == R.id.order_online) {
            isPay = 1;
        } else {
            isPay = 2;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.order_online) {
                    isPay = 1;
                } else {
                    isPay = 2;
                }
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this);
                alertDialog.setTitle("确定支付？");
                alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderInfo info = new OrderInfo();
                        info.setName(name.getText().toString());
                        info.setPhone(phone.getText().toString());
                        info.setAddress(address.getText().toString());
                        info.setIsPay(isPay);
                        info.setProInfo(orderInfo.getText().toString());
                        info.setTotalPrice(sum);
                        info.setOrderId(getRandomOrderId());
                        info.save(OrderActivity.this);
                        finish();
                        Toast.makeText(OrderActivity.this, "支付成功", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
    }

    private void initView() {
        name = (TextView) findViewById(R.id.order_name);
        phone = (TextView) findViewById(R.id.order_phone);
        address = (TextView) findViewById(R.id.order_detail);
        orderInfo = (TextView) findViewById(R.id.order_info);
        price = (TextView) findViewById(R.id.order_price);
        commit = (Button) findViewById(R.id.order_commit);
        radioGroup = (RadioGroup) findViewById(R.id.order_group);
        onLine = (RadioButton) findViewById(R.id.order_online);
        outLine = (RadioButton) findViewById(R.id.order_outline);
        orderInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void getAddressInfo(int position) {
        DBManager db = new DBManager(this);
        List<AddressInfo> info = db.queryAddress();
        if (info.size() != 0) {
            AddressInfo detail = info.get(position);
            name.setText(detail.getName());
            phone.setText(detail.getPhone());
            address.setText(detail.getAddress());
        } else {
            Toast.makeText(this, "请添加地址", Toast.LENGTH_LONG).show();
        }
    }

    private void getProInfo() {
        DBManager db = new DBManager(this);
        List<CartInfo> cart = db.query();
        StringBuffer sb = new StringBuffer();
        for (CartInfo info : cart) {
            sb.append("商品：");
            sb.append(info.getName() + "\n\r");
            sb.append("价格：");
            sb.append(info.getPrice() + "X" + info.getCount());
            sb.append("\n\r");
        }
        orderInfo.setText(sb.toString());
        price.setText("总价 ￥" + sum);
    }

    private String getRandomOrderId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String id = dateFormat.format(new Date()).toString();
        Random random = new Random();
        int i = random.nextInt(10);
        return id + i;
    }

}
