package com.example.huhu.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huhu.shopping.R;
import com.example.huhu.shopping.bean.HotInfo;
import com.example.huhu.shopping.bean.ProductInfo;

import java.util.List;

/**
 * Created by lenovo on 2016/5/13.
 */
public class HotAdapter extends BaseAdapter{
    private Context context;
    private List<HotInfo> data;
    private LayoutInflater mInflater;

    public HotAdapter(Context context, List<HotInfo> data) {
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
            convertView=mInflater.inflate(R.layout.item_hot,parent,false);
            mHolder.mTxtName= (TextView) convertView.findViewById(R.id.hot_item_title);
            mHolder.mImgPic= (ImageView) convertView.findViewById(R.id.hot_item_img);
            convertView.setTag(mHolder);
        }else{
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.mTxtName.setText(data.get(position).getName());
        mHolder.mImgPic.setImageResource(data.get(position).getPicture());

        return convertView;
    }

    public class ViewHolder{
        TextView mTxtName;
        ImageView mImgPic;
    }
}
