package com.example.huhu.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.huhu.shopping.bean.TabInfo;
import com.example.huhu.shopping.fragment.CartFragment;
import com.example.huhu.shopping.fragment.HomeFragment;
import com.example.huhu.shopping.fragment.HotFragment;
import com.example.huhu.shopping.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;

    private List<TabInfo> mTabs=new ArrayList<TabInfo>(4);

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mTabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        mInflater=LayoutInflater.from(this);
        mTabHost.setup(this,getSupportFragmentManager(), R.id.realtabcontent);
        initTab();
    }

    private void initTab() {
        TabInfo tab_home=new TabInfo("首页",R.drawable.selector_icon_home, HomeFragment.class);
        TabInfo tab_hot=new TabInfo("分类",R.drawable.selector_icon_hot, HotFragment.class);
        TabInfo tab_shopping=new TabInfo("购物车",R.drawable.selector_icon_cart, CartFragment.class);
        TabInfo tab_mine=new TabInfo("我的",R.drawable.selector_icon_mine, MineFragment.class);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_shopping);
        mTabs.add(tab_mine);

        for(TabInfo info:mTabs){

            TabHost.TabSpec spec=mTabHost.newTabSpec(info.getTitle());

            spec.setIndicator(buildIndicator(info));
            mTabHost.addTab(spec,info.getFragment(),null);

        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }

    private  View buildIndicator(TabInfo tab){


        View view=mInflater.inflate(R.layout.layout_tab_indicator,null);
        ImageView img = (ImageView) view.findViewById(R.id.indicator_img);
        TextView text = (TextView) view.findViewById(R.id.indicator_txt);

        img.setBackgroundResource(tab.getPicture());
        text.setText(tab.getTitle());

        return  view;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("确定要退出？");
        alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}
