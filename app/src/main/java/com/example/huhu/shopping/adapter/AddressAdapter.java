package com.example.huhu.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.huhu.shopping.R;
import com.example.huhu.shopping.bean.AddressInfo;

import java.util.List;

/**
 * Created by lenovo on 2016/5/17.
 */
public class AddressAdapter extends BaseAdapter {

    private List<AddressInfo> mInfo;
    private Context mContext;
    private LayoutInflater mInflater;

    private int index;

    private View.OnClickListener onDeleteListener;
    private View.OnClickListener onEditListener;

    public void setOnDeleteListener(View.OnClickListener onDeleteListener){
        this.onDeleteListener=onDeleteListener;
    }

    public void setOnEditListener(View.OnClickListener onEditListener){
        this.onEditListener=onEditListener;
    }

    public AddressAdapter(Context context, List<AddressInfo> info) {
        mInfo = info;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_address, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.item_address_name);
            holder.phone = (TextView) convertView.findViewById(R.id.item_address_phone);
            holder.address = (TextView) convertView.findViewById(R.id.item_address_detail);
            holder.check = (RadioButton) convertView.findViewById(R.id.item_address_check);

            holder.delete= (TextView) convertView.findViewById(R.id.item_address_delete);
            holder.delete.setOnClickListener(onDeleteListener);
            holder.edit= (TextView) convertView.findViewById(R.id.item_address_edit);
            holder.edit.setOnClickListener(onEditListener);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(mInfo.get(position).getName());
        holder.phone.setText(mInfo.get(position).getPhone());
        holder.address.setText(mInfo.get(position).getAddress());
        holder.delete.setTag(position);
        holder.edit.setTag(position);
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    index = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (index == position) {
            holder.check.setChecked(true);
            holder.check.setText("默认地址");
        } else {
            holder.check.setChecked(false);
            holder.check.setText("设为默认");
        }
        return convertView;
    }

    public class ViewHolder {
        TextView name;
        TextView phone;
        TextView address;
        RadioButton check;

        TextView delete;
        TextView edit;
    }
}
