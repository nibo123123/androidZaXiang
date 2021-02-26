package com.example.chencj.myapplication.multiprocess;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.chencj.myapplication.multiprocess.provider.MultiProcessProvider;

import java.util.List;

/**
 * Created by CHENCJ on 2021/2/24.
 */
public class MultiProcessSharedPreferencesManager {
    public static final String PROCESS_1 = "com.example.chencj.myapplication";
    public static final String SP_NAME = "multiProcessSharedPreferencesManager";

    private volatile static MultiProcessSharedPreferencesManager mSharedPreferencesManager;
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    private MultiProcessSharedPreferencesManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static MultiProcessSharedPreferencesManager getInstance(Context context) {
        if (mSharedPreferencesManager == null) {
            synchronized (MultiProcessSharedPreferencesManager.class) {
                if (mSharedPreferencesManager == null) {
                    mSharedPreferencesManager = new MultiProcessSharedPreferencesManager(context);
                }
            }
        }

        return mSharedPreferencesManager;
    }

    public void setString(String key, String value) {
        if (PROCESS_1.equals(getProcessName())) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        } else {
            MultiProcessProvider.setStringValue(mContext, key, value);
        }
    }

    public String getString(String key, String defValue) {
        if (PROCESS_1.equals(getProcessName())) {
            return mSharedPreferences.getString(key, defValue);
        } else {
            return MultiProcessProvider.getStringValue(mContext, key, defValue);
        }
    }

    public String getProcessName() {
        int pid = android.os.Process.myPid();
        String processName = null;

        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return null;
        }

        List<ActivityManager.RunningAppProcessInfo> runningApps = activityManager.getRunningAppProcesses();
        if (runningApps == null || runningApps.size() == 0) {
            return null;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : runningApps) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
            }
        }

        return processName;
    }
}
