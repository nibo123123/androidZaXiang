package com.example.chencj.myapplication.surfacefling;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by CHENCJ on 2021/1/8.
 */
public class SurfaceDemoActivity extends Activity {

    private GifSurfaceView gifSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gifSurfaceView = new GifSurfaceView(this);
        setContentView(gifSurfaceView);
        gifSurfaceView.setBackgroundResource((android.R.color.transparent));
        gifSurfaceView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(gifSurfaceView != null)gifSurfaceView.stop();
    }
}
