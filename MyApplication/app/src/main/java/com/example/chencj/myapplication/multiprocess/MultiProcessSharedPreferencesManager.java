package com.example.chencj.myapplication.multiprocess;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by CHENCJ on 2021/2/24.
 */
public class MultiProcessSharedPreferencesManager {
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
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }
}
