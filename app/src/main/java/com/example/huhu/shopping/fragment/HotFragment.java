package com.example.huhu.shopping.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.huhu.shopping.R;
import com.example.huhu.shopping.adapter.HotAdapter;
import com.example.huhu.shopping.bean.HotInfo;
import com.example.huhu.shopping.KindsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends android.support.v4.app.Fragment {

    private String[] hotName={"饮料","牛奶","水果","办公","清洁","洗护","零食"};
    private int[] hotPicture={R.mipmap.drink,R.mipmap.milk,R.mipmap.fruit,
            R.mipmap.bangong,R.mipmap.clear,R.mipmap.xishu,R.mipmap.snake};

    private List<HotInfo> mData;
    private GridView mGridView;
    private HotAdapter mHotAdapter;

    public HotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getData();
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_hot, container, false);
        mGridView= (GridView) view.findViewById(R.id.hot_frg_grid);
        mHotAdapter=new HotAdapter(getActivity(),mData);
        mGridView.setAdapter(mHotAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"点击的选项："+position,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(), KindsActivity.class);
                intent.putExtra("type",hotName[position]);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getData(){
        mData=new ArrayList<HotInfo>();
        for (int i=0;i<hotName.length;i++){
            HotInfo info=new HotInfo();
            info.setName(hotName[i]);
            info.setPicture(hotPicture[i]);
            mData.add(info);
        }
    }


}
