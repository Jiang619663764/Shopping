package com.example.huhu.shopping.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/5/11.
 */
public class CartListView extends ListView {

    /**
     * 屏幕的宽
     */
    private int mScreenWidth;
    /**
     * 按下时x的坐标
     */
    private int mDownX;
    /**
     * 按下时y的坐标
     */
    private int mDownY;
    /**
     * 删除按钮是否显示
     */
    private boolean isBtnShowing;
    /**
     * 删除按钮的宽度
     */
    private int mDeleteBtnWidth;
    /**
     * 当前处理的item
     */
    private ViewGroup mPointChild;
    /**
     * 当前处理的layoutparam
     */
    private LinearLayout.LayoutParams mLayoutParams;

    public CartListView(Context context) {
        this(context, null);
    }

    public CartListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CartListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取屏幕的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                performActionUp(ev);
                break;

            case MotionEvent.ACTION_MOVE:

                return performActionMove(ev);

            case MotionEvent.ACTION_DOWN:
                performActionDown(ev);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 处理ACTION_DOWN事件
     *
     * @param ev
     */
    private void performActionDown(MotionEvent ev) {
        if (isBtnShowing) {
            turnToNormal();
        }
        mDownX = (int) ev.getX();
        mDownY = (int) ev.getY();
        //获取当前点的item
        mPointChild = (ViewGroup) getChildAt(pointToPosition(mDownX, mDownY) - getFirstVisiblePosition());
        //获取删除按钮的宽度
        mDeleteBtnWidth = mPointChild.getChildAt(1).getLayoutParams().width;
        mLayoutParams = (LinearLayout.LayoutParams) mPointChild.getChildAt(0).getLayoutParams();
        // 为什么要重新设置layout_width 等于屏幕宽度
        // 因为match_parent时，不管你怎么滑，都不会显示删除按钮
        // why？ 因为match_parent时，ViewGroup就不去布局剩下的view
        mLayoutParams.width = mScreenWidth;
        mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
    }

    /**
     * 处理ACTION_MOVE事件
     *
     * @param ev
     * @return
     */
    private boolean performActionMove(MotionEvent ev) {

        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();
        if (Math.abs(nowX - mDownX) > Math.abs(nowY - mDownY)) {
            //如果向左滑动
            if (nowX < mDownX) {
                //计算要偏移的距离
                int scroll = (nowX - mDownX) / 2;
                //如果大于删除按钮的宽度，则设置为删除按钮的宽
                if (-scroll > mDeleteBtnWidth) {
                    scroll = -mDeleteBtnWidth;
                }
                //重新设置leftMargin
                mLayoutParams.leftMargin = scroll;
                mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 处理ACTION_UP事件
     *
     * @param ev
     */
    private void performActionUp(MotionEvent ev) {

        //偏移量大于button的一半时，显示button，否则恢复默认
        if (-mLayoutParams.leftMargin > mDeleteBtnWidth / 2) {
            mLayoutParams.leftMargin = -mDeleteBtnWidth;
            isBtnShowing = true;
        } else {
            turnToNormal();
        }
        mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
    }

    /**
     * 当前是否可点击
     *
     * @return 是否可点击
     */
    public boolean canClick() {

        return !isBtnShowing;
    }

    /**
     * 正常显示状态
     */
    public void turnToNormal() {
        mLayoutParams.leftMargin = 0;
        mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
        isBtnShowing = false;
    }
}
