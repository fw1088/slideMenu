package com.example.fangwei.slidemenu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

/**
 * 原本想封装一下，可是太懒了，先这样
 * slideMenu.java
 * Created by fangwei on 15/10/29.
 */
public class slideMenu extends FrameLayout {

    //设置的菜单的布局
    private int mMenuLayoutId;

    //设置的主菜单的布局
    private int mMainLayoutId;

    //设置主菜单的偏移
    private int mMainLeftMargin = 40;

    //设置侧滑菜单的偏移
    private int mMenuLeftMargin = 40;

    private final String TAG = "SlideMenu";

    //设备上下文
    private Context mcontext;

    //是否是第一次进行测量，获取原始的宽高
    boolean isFirst = true;

    boolean isdisfirst = true;

    private ViewDragHelper mViewDragHelper;

    private LinearLayout mMenuView, mMainView;

    //获取菜单的宽度
    private int mWidth;

    //获取菜单的底部坐标
    private int bottom;

    private int mainTop;

    private int mainWidth;

    //获得主视图的底部坐标
    private int mainBottom;

    //获得初始的菜单的顶部坐标
    private int mtop;

    //设置x方向上移动的距离
    private float xdel;

    //设置y每次移动的距离
    private float ydel;

    //x变化的方向，向左为－
    private int mdx;

    //测试手按下的位置
    private float mdownx;

    //设置垂直方向上的偏移
    private float ydis = 30;

    //设置菜单隐藏的距离
    private float xdisappear = 50;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getX() < 50) {
            return mViewDragHelper.shouldInterceptTouchEvent(ev);
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }
    
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mdownx = event.getX();
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public slideMenu(Context context) {
        super(context);
        initView();
        mcontext = context;
    }

    public slideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        mcontext = context;
    }

    public slideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        mcontext = context;
    }

    /*
    ＊动态加载listView
     */
    private void addListView()
    {
        //获得inflater有3种方式，一种是通过getLayoutInflater();
        //一种是通过getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater.from(context)的静态方法;
        LayoutInflater inflater = LayoutInflater.from(mcontext);

        ListView list = (ListView) inflater.inflate(R.layout.menulayout, null);

        //动态加载布局
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        lp.leftMargin = mMenuLeftMargin;

        //设置布局参数
        list.setLayoutParams(lp);

        mMenuView.addView(list);

        ListView listmain = (ListView) inflater.inflate(R.layout.mainlayout,null);

        lp.leftMargin = mMainLeftMargin;

        listmain.setLayoutParams(lp);

        mMainView.addView(listmain);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mcontext, android.R.layout.simple_expandable_list_item_1);

        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");

        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");

        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");
        adapter.add("String1");
        adapter.add("String12");

        list.setAdapter(adapter);
        listmain.setAdapter(adapter);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = (LinearLayout) getChildAt(0);
        mMainView = (LinearLayout) getChildAt(1);
        addListView();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mMainView.getLeft() == 0 && mMenuView.getVisibility() == VISIBLE) {
            mMenuView.setVisibility(INVISIBLE);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void initView() {
        mMainLayoutId = -1;
        mMenuLayoutId = -1;
        mViewDragHelper = ViewDragHelper.create(slideMenu.this, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.e(TAG, "====>" + mdownx + "|" + dipsToPixels(mMainView.getLeft()));
            return mMainView == child || mMenuView == child;

        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.e("slide", "====>" + left);
            if (child == mMainView) {
                if (left > mWidth) {
                    left = mWidth;
                } else if (left < 0) {
                    left = 0;
                }
            } else if (child == mMenuView) {
                if (left > 0) {
                    left = 0;
                }
            }
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.e(TAG, "=====>" + xvel);
            if (mdx < 0) {
                mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(slideMenu.this);
            } else if (mMainView.getLeft() > 15) {
                mViewDragHelper.smoothSlideViewTo(mMainView, mWidth, 0);
                ViewCompat.postInvalidateOnAnimation(slideMenu.this);
            } else {
                mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(slideMenu.this);
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            mdx = dx;
            if (isFirst) {
                mWidth = mMenuView.getMeasuredWidth();
                bottom = mMenuView.getBottom();
                isFirst = false;
                xdel = xdisappear / (float) mWidth;
                ydel = ydis / (float) mWidth;
                mainTop = mMainView.getTop();
                mainBottom = mMainView.getBottom();
                bottom = mMenuView.getBottom();
                mtop = mMenuView.getTop();
                mainWidth = mMainView.getWidth();
            }
            Log.e(TAG, "====>" + dx);
            if ((changedView == mMainView || changedView == mMenuView) && mMainView.getLeft() < mWidth - 20 && dx > 0 && isdisfirst) {
                isFirst = false;
                mMenuView.layout((int) -xdisappear, mtop + (int) ydis, (int) -xdisappear + mMenuView.getWidth(), bottom - (int) ydis);
                mMenuView.setVisibility(View.VISIBLE);
            }

            if (changedView == mMainView || changedView == mMenuView) {
                int leftMainMenu = mMainView.getLeft();
                mMenuView.layout((int) (-xdisappear + (float) leftMainMenu * xdel + 0.5), (int) (((float) mtop + ydis) - ((float) leftMainMenu) * ydel + 0.5), (int) (-50f + mWidth + xdel * leftMainMenu + 0.5), (int) (bottom - ydis + leftMainMenu * ydel + 0.5));

                mMainView.layout(leftMainMenu, (int) (mainTop + ydel * leftMainMenu + 0.5), leftMainMenu + mainWidth, (int) (mainBottom - ydel * leftMainMenu + 0.5));
            }
            if ((changedView == mMainView) && left == 0 && dx < 0) {
                mMenuView.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            isdisfirst = true;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }
    };


    private int dipsToPixels(float dips) {

        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dips * scale + 0.5f);
    }

    private int pixelTodips(float pixel) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixel / scale + 0.5f);
    }
}
