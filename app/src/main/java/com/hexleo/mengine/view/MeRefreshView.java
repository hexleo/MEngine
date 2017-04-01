package com.hexleo.mengine.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hexleo.mengine.util.MLog;

/**
 * Created by hexleo on 2017/3/21.
 */

public class MeRefreshView extends SwipeRefreshLayout {
    private static final String TAG = "MeRefreshView";

    private boolean canScroll = true;

    public MeRefreshView(Context context) {
        super(context);
    }

    public MeRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!canScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
