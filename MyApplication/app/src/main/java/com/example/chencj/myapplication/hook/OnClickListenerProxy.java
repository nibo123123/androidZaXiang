package com.example.chencj.myapplication.hook;

import android.util.Log;
import android.view.View;

/**
 * Created by CHENCJ on 2020/12/24.
 */

public class OnClickListenerProxy implements View.OnClickListener {
    private View.OnClickListener onClickListener;
    public OnClickListenerProxy(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        Log.d("OnClickListenerProxy chencj ", "onClick: 123");
        if(onClickListener!=null){
            onClickListener.onClick(v);
        }
    }
}
