package com.example.fangwei.slidemenu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

/**
 * Created by fangwei on 15/10/29.
 */
public class slideMenu extends FrameLayout {

    private Context mcontext;

    boolean isFirst = true;

    boolean isdisfirst = true;

    private ViewDragHelper mViewDragHelper;

    private LinearLayout mMenuView, mMainView;

    private int mHeight;

    private int mWidth;

    private int bottom;

    private int mainTop;

    private int mainWidth;

    private int mainBottom;

    private int mtop;

    private float xdel;

    private float ydel;

    private int mdx;

    private float mdownx;

    private float ydis = 60;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

//        if (mMainView.getLeft()==0&&mMenuView.getVisibility() == VISIBLE)
//        {
//            mMenuView.setVisibility(INVISIBLE);
//        }

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
        context = mcontext;
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = (LinearLayout) getChildAt(0);
        mMainView = (LinearLayout) getChildAt(1);

        ListView list = (ListView)mMenuView.findViewById(R.id.menulist);

        ListView listmain = (ListView)mMainView.findViewById(R.id.mainlist);

        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(mcontext,android.R.layout.simple_expandable_list_item_1);

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mMainView.getLeft() ==0&& mMenuView.getVisibility()== VISIBLE)
        {
            mMenuView.setVisibility(INVISIBLE);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void initView() {
        mViewDragHelper = ViewDragHelper.create(slideMenu.this, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.e("down", "====>" + mdownx + "|" + dipsToPixels(mMainView.getLeft()));
//            if (mdownx<500&&mMainView == child)
//            {
//                return true;
//            }
//            else if (mMainView.getLeft()> mWidth - 10){
            return mMainView == child || mMenuView == child;
            // }
            // return false;
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
            }
            else if (child == mMenuView)
            {
                if (left>0)
                {
                    left = 0;
                }
            }
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.e("testbig", "=====>" + xvel);
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
                xdel = 50f / (float) mWidth;
                ydel = ydis / (float) mWidth;
                mainTop = mMainView.getTop();
                mainBottom = mMainView.getBottom();
                mHeight = mMenuView.getMeasuredHeight();
                bottom = mMenuView.getBottom();
                mtop = mMenuView.getTop();
                mainWidth = mMainView.getWidth();
            }
            Log.e("big", "====>" + dx);
            if ((changedView == mMainView || changedView == mMenuView) && mMainView.getLeft() < mWidth - 20 && dx > 0 && isdisfirst) {
                isFirst = false;
                mMenuView.layout(-50, mtop + (int) ydis, -50 + mMenuView.getWidth(), bottom - (int) ydis);
                mMenuView.setVisibility(View.VISIBLE);
            }

            if (changedView == mMainView || changedView == mMenuView) {
                int lefttest = mMainView.getLeft();
                mMenuView.layout((int) (-50f + (float) lefttest * xdel + 0.5), (int) (((float) mtop + ydis) - ((float) lefttest) * ydel + 0.5), (int) (-50f + mWidth + xdel * lefttest + 0.5), (int) (bottom - ydis + lefttest * ydel + 0.5));

                mMainView.layout(lefttest, (int) (mainTop + ydel * lefttest + 0.5), lefttest + mainWidth, (int) (mainBottom - ydel * lefttest + 0.5));
            }
            if ((changedView == mMainView)&&left==0 && dx < 0) {
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
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, getResources().getDisplayMetrics());
    }
}
