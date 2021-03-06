package com.example.huhu.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huhu.shopping.bean.AddressInfo;
import com.example.huhu.shopping.bean.CartInfo;
import com.example.huhu.shopping.bean.OrderInfo;
import com.example.huhu.shopping.bean.ProductInfo;
import com.example.huhu.shopping.db.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PayActivity extends AppCompatActivity {
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

    List<ProductInfo> info;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pay);
        initView();
        Intent intent = getIntent();
        position = intent.getIntExtra("OrderPosition", 0);
        info = (List<ProductInfo>) intent.getSerializableExtra("OrderInfo");
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PayActivity.this);
                alertDialog.setTitle("确定支付？");
                alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderInfo infos = new OrderInfo();
                        infos.setName(name.getText().toString());
                        infos.setPhone(phone.getText().toString());
                        infos.setAddress(address.getText().toString());
                        infos.setIsPay(isPay);
                        infos.setProInfo(orderInfo.getText().toString());
                        infos.setTotalPrice(Float.parseFloat(info.get(position).getPrice()));
                        infos.setOrderId(getRandomOrderId());
                        infos.save(PayActivity.this);
                        finish();
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_LONG).show();
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
        name = (TextView) findViewById(R.id.pay_name);
        phone = (TextView) findViewById(R.id.pay_phone);
        address = (TextView) findViewById(R.id.pay_detail);
        orderInfo = (TextView) findViewById(R.id.pay_info);
        price = (TextView) findViewById(R.id.pay_price);
        commit = (Button) findViewById(R.id.pay_commit);
        radioGroup = (RadioGroup) findViewById(R.id.pay_group);
        onLine = (RadioButton) findViewById(R.id.pay_online);
        outLine = (RadioButton) findViewById(R.id.pay_outline);
    }

    private String getRandomOrderId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String id = dateFormat.format(new Date()).toString();
        Random random = new Random();
        int i = random.nextInt(10);
        return id + i;
    }

    private void getProInfo() {

        StringBuffer sb = new StringBuffer();

        sb.append("商品：");
        sb.append(info.get(position).getName() + "\n\r");
        sb.append("价格：");
        sb.append(info.get(position).getPrice() + "X" + 1);
        sb.append("\n\r");

        orderInfo.setText(sb.toString());
        price.setText("总价 ￥" + info.get(position).getPrice());
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
}
