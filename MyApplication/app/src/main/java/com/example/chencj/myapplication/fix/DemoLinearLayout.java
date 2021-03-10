package com.example.chencj.myapplication.fix;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by CHENCJ on 2021/3/10.
 */

public class DemoLinearLayout extends LinearLayout {
    public DemoLinearLayout(Context context) {
        super(context);
    }

    public DemoLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("DemoLinearLayout chencj ", "dispatchTouchEvent: event="+ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("DemoLinearLayout chencj ", "onInterceptTouchEvent: ev"+ev);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("DemoLinearLayout chencj ", "onTouchEvent: event="+event);
        return true;
    }
}
