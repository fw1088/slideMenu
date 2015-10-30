package com.example.fangwei.slidemenu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by fangwei on 15/10/29.
 */
public class slideMenu extends FrameLayout {

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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getX()<50) {
            return mViewDragHelper.shouldInterceptTouchEvent(ev);
        }
        else
        {
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
    }

    public slideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public slideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = (LinearLayout) getChildAt(0);
        mMainView = (LinearLayout) getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void initView() {
        mViewDragHelper = ViewDragHelper.create(slideMenu.this, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.e("down","====>"+mdownx+"|"+dipsToPixels(mMainView.getLeft()));
//            if (mdownx<500&&mMainView == child)
//            {
//                return true;
//            }
//            else if (mMainView.getLeft()> mWidth - 10){
                return mMainView == child;
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
            if (left > mWidth) {
                left = mWidth;
            } else if (left < 0) {
                left = 0;
            }
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //Log.e("testbig","=====>"+xvel);
            if (mdx < 0) {
                mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(slideMenu.this);
            } else if (mMainView.getLeft()>15){
                mViewDragHelper.smoothSlideViewTo(mMainView, mWidth, 0);
                ViewCompat.postInvalidateOnAnimation(slideMenu.this);
            }
            else
            {
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
                ydel = 40f / (float) mWidth;
                mainTop = mMainView.getTop();
                mainBottom = mMainView.getBottom();
                mHeight = mMenuView.getMeasuredHeight();
                bottom = mMenuView.getBottom();
                mtop = mMenuView.getTop();
                mainWidth = mMainView.getWidth();
            }
            Log.e("big","====>"+dx);
            if (changedView == mMainView && mMainView.getLeft()<mWidth-20&&dx>0&&isdisfirst) {
                isFirst = false;
                mMenuView.layout(-50, mtop + 40, -50 + mMenuView.getWidth(), bottom - 40);
                mMenuView.setVisibility(View.VISIBLE);
            }

            if (changedView == mMainView) {
                mMenuView.layout((int) (-50f + (float) left * xdel + 0.5), (int) (((float) mtop + 40f) - ((float) left) * ydel + 0.5), (int) (-50f + mWidth + xdel * left + 0.5), (int) (bottom - 40f + left * ydel + 0.5));

                mMainView.layout(left, (int) (mainTop + ydel * left + 0.5), left + mainWidth, (int) (mainBottom - ydel * left + 0.5));
            }
            if (left <3 && dx < 0)
            {
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


    private int dipsToPixels(float dips){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, getResources().getDisplayMetrics());
    }
}
