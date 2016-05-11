package com.example.huhu.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huhu.shopping.R;
import com.example.huhu.shopping.bean.ProductInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<ProductInfo> data;
    private LayoutInflater mInflater;

    public ProductAdapter(Context context, List<ProductInfo> data) {
        this.context = context;
        this.data = data;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if(convertView==null){
            mHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_product,parent,false);
            mHolder.mTxtName= (TextView) convertView.findViewById(R.id.item_pro_name);
            mHolder.mTxtIntro= (TextView) convertView.findViewById(R.id.item_pro_intro);
            mHolder.mTxtPrice= (TextView) convertView.findViewById(R.id.item_pro_price);
            mHolder.mImgPic= (ImageView) convertView.findViewById(R.id.item_pro_img);
            convertView.setTag(mHolder);
        }else{
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.mTxtName.setText(data.get(position).getName());
        mHolder.mTxtIntro.setText(data.get(position).getIntro());
        mHolder.mTxtPrice.setText("￥"+data.get(position).getPrice());
        mHolder.mImgPic.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }

    public class ViewHolder{
        TextView mTxtName;
        TextView mTxtPrice;
        TextView mTxtIntro;
        ImageView mImgPic;
    }
}
