package com.example.chencj.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;


import java.util.concurrent.Executor;

/**
 * Created by CHENCJ on 2020/11/9.
 */

public class App extends Application {
   // private RefWatcher refWatcher;
    private static Handler mHandler;

    public static Executor mThreadPool = AsyncTask.THREAD_POOL_EXECUTOR;
    public static Handler getmHandler() {
        return mHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //refWatcher= setupLeakCanary();
        mHandler = new Handler();
    }

    /*private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }*/

   /* public static RefWatcher getRefWatcher(Context context) {
        App leakApplication = (App) context.getApplicationContext();
        return leakApplication.refWatcher;
    }*/
}
