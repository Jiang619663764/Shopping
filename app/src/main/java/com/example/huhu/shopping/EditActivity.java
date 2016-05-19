package com.example.huhu.shopping;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huhu.shopping.bean.AddressInfo;
import com.example.huhu.shopping.db.DBManager;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mName;
    private EditText mPhone;
    private TextView mArea;
    private EditText mAddress;
//    private RadioButton mIsDefault;
    private Button mBtnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit);
        initView();
        mArea.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    private void initView() {
        mName= (EditText) findViewById(R.id.edit_edt_name);
        mPhone= (EditText) findViewById(R.id.edit_edt_phone);
        mArea= (TextView) findViewById(R.id.edit_txt_area);
        mAddress= (EditText) findViewById(R.id.edit_edt_address);
//        mIsDefault= (RadioButton) findViewById(R.id.edit_btn_default);
        mBtnSave= (Button) findViewById(R.id.edit_btn_save);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_btn_save:
                AddressInfo info=new AddressInfo();
                info.setName(mName.getText().toString());
                info.setPhone(mPhone.getText().toString());
                info.setAddress(mArea.getText().toString() + mAddress.getText().toString());
//                if(mIsDefault.isChecked()){
//                    info.setIsDefault(1);
//                }else {
                    info.setIsDefault(2);
//                }
                DBManager manager=new DBManager(EditActivity.this);
                manager.addAddress(info);
                Toast.makeText(EditActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.edit_txt_area:
                break;
        }
    }
}
