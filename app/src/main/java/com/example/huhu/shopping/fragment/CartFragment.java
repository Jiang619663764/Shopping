package com.example.huhu.shopping.fragment;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.huhu.shopping.R;
import com.example.huhu.shopping.adapter.CartAdapter;
import com.example.huhu.shopping.bean.CartInfo;
import com.example.huhu.shopping.db.DBManager;
import com.example.huhu.shopping.view.CartListView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends android.support.v4.app.Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    private CartListView mListView;
    private CartAdapter mCartAdapter;
    private List<CartInfo> mList;

    private TextView mTotalPrice;
    private Button mBtnPay;

    //创建观察者
    private DataSetObserver sumObserver=new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            float sum=0;
            for (int i=0;i<mList.size();i++){
                sum+=mList.get(i).getPrice()*mList.get(i).getCount();

            }
            mTotalPrice.setText("￥"+sum);
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };

    public CartFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initData();
        View view=inflater.inflate(R.layout.fragment_cart, container, false);

        mTotalPrice= (TextView) view.findViewById(R.id.cart_frg_total_price);
        mBtnPay= (Button) view.findViewById(R.id.cart_frg_pay);
        mListView= (CartListView) view.findViewById(R.id.cart_frg_listview);
        mCartAdapter=new CartAdapter(getActivity(),mList);

        mCartAdapter.registerDataSetObserver(sumObserver);
        mCartAdapter.notifyDataSetChanged();

        mListView.setAdapter(mCartAdapter);

        mCartAdapter.setOnAddNumListener(this);
        mCartAdapter.setOnSubNumListener(this);
        mCartAdapter.setOnDeleteListener(this);
        mListView.setOnItemClickListener(this);
        mBtnPay.setOnClickListener(this);
        return view;
    }

    private void initData() {
        DBManager db=new DBManager(getActivity());
        mList=db.query();
    }


    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.btn_add_shopping_item:
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    int position = (Integer) tag;
                    int num = mList.get(position).getCount();
                    float price=mList.get(position).getPrice();
                    num++;
                    mList.get(position).setCount(num);
                    mList.get(position).setPrice(price);
                    mCartAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_sub_shopping_item:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    int num = mList.get(position).getCount();
                    float price=mList.get(position).getPrice();
                    if (num > 1) {
                        num--;
                        mList.get(position).setCount(num);
                        mList.get(position).setPrice(price);
                        mCartAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.delete_shopping_item:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    mList.remove(position);
                    mCartAdapter.notifyDataSetChanged();
                    mListView.turnToNormal();
                }
                break;
            case R.id.cart_frg_pay:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCartAdapter.unregisterDataSetObserver(sumObserver);
    }
}
