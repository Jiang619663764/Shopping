package com.example.huhu.shopping.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.huhu.shopping.DetailActivity;
import com.example.huhu.shopping.R;
import com.example.huhu.shopping.adapter.ProductAdapter;
import com.example.huhu.shopping.bean.ProductInfo;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    private PullToRefreshListView mRefreshListView;

    public static List<ProductInfo> mDatas;

    private ProductAdapter mProductAdapter;

    private Handler mHandler;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        getInfo(getActivity());
        mRefreshListView= (PullToRefreshListView) view.findViewById(R.id.refresh_home_frg);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mDatas= (List<ProductInfo>) msg.obj;
                mProductAdapter=new ProductAdapter(getActivity(),mDatas);

                //设置适配器
                mRefreshListView.setAdapter(mProductAdapter);
                //子选项点击事件
                mRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("position",position-1);
                        startActivity(intent);
                    }
                });
                //刷新数据
                mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                    @Override
                    public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                    }
                });
            }
        };
        return view;
    }

    /**
     *
     * @param context
     * @return
     */
    private void getInfo(final Context context){

        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<ProductInfo> query=new BmobQuery<ProductInfo>();
                query.findObjects(context, new FindListener<ProductInfo>() {
                    @Override
                    public void onSuccess(List<ProductInfo> list) {
                        Message msg=new Message();
                        msg.obj=list;
                        mHandler.sendMessage(msg);
                        Log.e("Fragment","------------------------"+list.size());
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("Fragment", "------------------------Error");
                    }
                });
            }
        }).start();

    }


}
