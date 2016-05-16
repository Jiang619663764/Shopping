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

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.huhu.shopping.DetailActivity;
import com.example.huhu.shopping.R;
import com.example.huhu.shopping.adapter.ProductAdapter;
import com.example.huhu.shopping.bean.ProductInfo;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    private SliderLayout mSliderLayout;

    private PullToRefreshListView mRefreshListView;

    public static List<ProductInfo> mDatas;

    private ProductAdapter mProductAdapter;

    private Handler mHandler;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getInfo(getActivity());
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        View layout= LayoutInflater.from(getActivity()).inflate(R.layout.layout_banner, null);

        mRefreshListView= (PullToRefreshListView) view.findViewById(R.id.refresh_home_frg);
        ListView listView = mRefreshListView.getRefreshableView();
        //轮播图的设定
        mSliderLayout= (SliderLayout) layout.findViewById(R.id.slider);
        addBanner();
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
        listView.addHeaderView(layout);


        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mDatas= (List<ProductInfo>) msg.obj;
                mProductAdapter=new ProductAdapter(getActivity(),mDatas);
                //设置适配器
                mRefreshListView.setAdapter(mProductAdapter);

            }
        };
        //子选项点击事件
        mRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("position",position-1);
                intent.putExtra("data", (Serializable) mDatas);
                startActivity(intent);
            }
        });
        //刷新数据
        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        return view;
    }

    private void addBanner(){
        TextSliderView tsv=new TextSliderView(getActivity());
        tsv.image(R.mipmap.banner1);
        tsv.description("一分钱就能抢");
        mSliderLayout.addSlider(tsv);
        TextSliderView tsv1=new TextSliderView(getActivity());
        tsv1.image(R.mipmap.banner2);
        tsv1.description("吃货的季节");
        mSliderLayout.addSlider(tsv1);
        TextSliderView tsv2=new TextSliderView(getActivity());
        tsv2.image(R.mipmap.banner3);
        tsv2.description("好吃来不停");
        mSliderLayout.addSlider(tsv2);
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
