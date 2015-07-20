package com.fwollo.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPager with disabled content changing via swipe
 */
public class NonSwipeableViewPager extends ViewPager {

    private boolean isSettingHeight;

    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    public void setVariableHeight(View currentView) {
        // super.measure() calls finishUpdate() in adapter, so need this to stop infinite loop
        if (!this.isSettingHeight) {
            this.isSettingHeight = true;

            int maxChildHeight = 0;
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
            currentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED));
            maxChildHeight = currentView.getMeasuredHeight();
            int height = maxChildHeight + getPaddingTop() + getPaddingBottom();
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            super.measure(widthMeasureSpec, heightMeasureSpec);
            requestLayout();

            this.isSettingHeight = false;
        }
    }
}
