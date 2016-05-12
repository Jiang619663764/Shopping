package com.example.huhu.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huhu.shopping.R;
import com.example.huhu.shopping.bean.CartInfo;
import com.example.huhu.shopping.bean.ProductInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class CartAdapter extends BaseAdapter {
    private Context mContext;

    private List<CartInfo> mShoppingInfo;

    //设置接口
    private View.OnClickListener onAddNum;
    private View.OnClickListener onSubNum;
    private View.OnClickListener onDeleteList;

    //设置接口方法
    public void setOnDeleteListener(View.OnClickListener onDeleteList){this.onDeleteList=onDeleteList;}

    public void setOnAddNumListener(View.OnClickListener onAddNum) {
        this.onAddNum = onAddNum;
    }

    public void setOnSubNumListener(View.OnClickListener onSubNum) {
        this.onSubNum = onSubNum;
    }

    public CartAdapter(Context context, List<CartInfo> shoppingInfo) {
        mContext = context;
        mShoppingInfo = shoppingInfo;
    }

    @Override
    public int getCount() {
        return mShoppingInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mShoppingInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = null;
        if (convertView != null) {
            v = convertView;
        } else {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        }
        ViewHolder holder = (ViewHolder) v.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.mShoppingImg = (ImageView) v.findViewById(R.id.iv_shopping_item);
            holder.mShoppingName = (TextView) v.findViewById(R.id.txt_name_shopping_item);
            holder.mShoppingDetail = (TextView) v.findViewById(R.id.txt_detail_shopping_item);
            holder.mShoppingPrice = (TextView) v.findViewById(R.id.txt_prive_shopping_item);
            holder.mShoppingCount = (TextView) v.findViewById(R.id.txt_count_shopping_item);

            //设置接口回调，注意参数不是上下文，它需要ListView所在的Activity或者Fragment处理接口回调方法
            holder.mShoppingAdd = (Button) v.findViewById(R.id.btn_add_shopping_item);
            holder.mShoppingAdd.setOnClickListener(onAddNum);

            holder.mShoppingSub = (Button) v.findViewById(R.id.btn_sub_shopping_item);
            holder.mShoppingSub.setOnClickListener(onSubNum);

            holder.mShoppingDelete= (TextView) v.findViewById(R.id.delete_shopping_item);
            holder.mShoppingDelete.setOnClickListener(onDeleteList);
        }
        holder.mShoppingImg.setImageResource(R.mipmap.ic_launcher);
        holder.mShoppingName.setText(mShoppingInfo.get(position).getName());
        holder.mShoppingDetail.setText(mShoppingInfo.get(position).getIntro());
        holder.mShoppingPrice.setText("￥"+mShoppingInfo.get(position).getPrice()*mShoppingInfo.get(position).getCount());
        holder.mShoppingCount.setText(mShoppingInfo.get(position).getCount()+"");

        // 设置Tag，用于判断用户当前点击的哪一个列表项的按钮
        holder.mShoppingAdd.setTag(position);
        holder.mShoppingSub.setTag(position);
        holder.mShoppingDelete.setTag(position);

        v.setTag(holder);

        return v;
    }

    private class ViewHolder {

        private ImageView mShoppingImg;
        private TextView mShoppingName;
        private TextView mShoppingDetail;
        private TextView mShoppingPrice;

        private TextView mShoppingCount;
        private Button mShoppingAdd;
        private Button mShoppingSub;

        private TextView mShoppingDelete;
    }
}
