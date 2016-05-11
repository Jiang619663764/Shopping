package com.example.huhu.shopping.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.huhu.shopping.R;

/**
 * Created by Administrator on 2016/5/11.
 */
public class MyToolBar extends Toolbar {

    private LayoutInflater mInflater;

    private TextView mTextTitle;

    private ImageButton mRightButton;

    private EditText mSearchView;

    private View mView;

    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolBar, defStyleAttr, 0);

            boolean isShowing=a.getBoolean(R.styleable.MyToolBar_isShowing, false);

            final Drawable icon = a.getDrawable(R.styleable.MyToolBar_rightButton);
            if (icon != null) {
                setRightButton(icon, isShowing);

            }

            a.recycle();
        }
    }

    private void setRightButton(Drawable icon, boolean isShowing) {

        if (mRightButton != null) {
            mRightButton.setImageDrawable(icon);
            if(isShowing){
                mRightButton.setVisibility(VISIBLE);
            }else {
                mRightButton.setVisibility(GONE);
            }

        }
    }

    private void initView() {

        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.layout_toolbar, null);

            mTextTitle = (TextView) mView.findViewById(R.id.toorbar_title);
            mSearchView = (EditText) mView.findViewById(R.id.toorbar_searchView);
            mRightButton = (ImageButton) mView.findViewById(R.id.toorbar_rightButton);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, layoutParams);
        }
    }

    @Override
    public void setTitle(int resId) {

        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {

        initView();

        if (mTextTitle != null) {
            mTextTitle.setText(title);
            mTextTitle.setVisibility(VISIBLE);
        }
    }
}
