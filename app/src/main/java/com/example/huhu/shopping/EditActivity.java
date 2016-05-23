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

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.huhu.shopping.bean.AddressInfo;
import com.example.huhu.shopping.db.DBManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName;
    private EditText mPhone;
    private TextView mArea;
    private EditText mAddress;
    private Button mBtnSave;

    private OptionsPickerView mPickerView;
    private JSONObject mJsonData = null;
    ArrayList<ArrayList<ArrayList<String>>> a = new ArrayList<ArrayList<ArrayList<String>>>();
    ArrayList<ArrayList<String>> b = new ArrayList<ArrayList<String>>();
    ArrayList<String> c = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit);
        initView();
        mPickerView = new OptionsPickerView(this);
        mPickerView.setPicker(c, b, a, true);
        mPickerView.setCyclic(false, false, false);
        mPickerView.setTitle("选择城市");
        //设置默认的三个级别的选中位置
        //监听确定选择按钮
        mPickerView.setSelectOptions(0, 0, 0);
        mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String str = c.get(options1) + b.get(options1).get(option2) +
                        a.get(options1).get(option2).get(options3);
                mArea.setText(str);
            }
        });
        mArea.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    private void initView() {
        getJsonData();
        parseJsonData();
        mName = (EditText) findViewById(R.id.edit_edt_name);
        mPhone = (EditText) findViewById(R.id.edit_edt_phone);
        mArea = (TextView) findViewById(R.id.edit_txt_area);
        mAddress = (EditText) findViewById(R.id.edit_edt_address);
//        mIsDefault= (RadioButton) findViewById(R.id.edit_btn_default);
        mBtnSave = (Button) findViewById(R.id.edit_btn_save);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_btn_save:
                AddressInfo info = new AddressInfo();
                info.setName(mName.getText().toString());
                info.setPhone(mPhone.getText().toString());
                info.setAddress(mArea.getText().toString() + mAddress.getText().toString());

                info.setIsDefault(2);
                DBManager manager = new DBManager(EditActivity.this);
                manager.addAddress(info);
                Toast.makeText(EditActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.edit_txt_area:
                mPickerView.show();
                break;
        }
    }

    /**
     * 获取本地Json数据
     */
    private void getJsonData() {

        try {
            StringBuffer sbf = new StringBuffer();
            InputStream is = this.getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[is.available()];
            while ((len = is.read(buf)) != -1) {
                sbf.append(new String(buf, 0, len, "gbk"));
            }
            is.close();
            mJsonData = new JSONObject(sbf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析Json数据
     */
    private void parseJsonData() {

        try {

            ArrayList<String> subArea = null;
            ArrayList<String> subCity = null;

            JSONArray jsonArray = mJsonData.getJSONArray("citylist");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("p");// 省名字

                c.add(province);
                subCity = new ArrayList<String>();
                ArrayList<ArrayList<String>> subArea02 = new ArrayList<ArrayList<String>>();

                JSONArray jsonCs = null;
                jsonCs = jsonP.getJSONArray("c");

                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("n");// 市名字

                    subCity.add(city);
                    subArea = new ArrayList<String>();

                    JSONArray jsonAreas = null;
                    jsonAreas = jsonCity.getJSONArray("a");

                    for (int k = 0; k < jsonAreas.length(); k++) {

                        String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
                        subArea.add(area);

                    }
                    subArea02.add(subArea);
                }
                a.add(subArea02);
                b.add(subCity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonData = null;
    }
}
