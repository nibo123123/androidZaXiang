package com.example.chencj.myapplication.multiprocess.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.chencj.myapplication.multiprocess.MultiProcessSharedPreferencesManager;

/**
 * Created by CHENCJ on 2021/2/24.
 */

public class MultiProcessProvider extends ContentProvider {
    private static String EXTRA_TYPE = "type";
    private static String EXTRA_KEY = "key";
    private static String EXTRA_VALUE = "value";
    private static final int TYPE_STRING = 1;
    public static final Uri MY_CONTENT_PROVIDER_URI = Uri.parse("content://com.example.chencj.myapplication.multiprocess");
    private static final int LENGTH_CONTENT_URI	= MY_CONTENT_PROVIDER_URI.toString().length() + 1;

    @Override
    public boolean onCreate() {
        return false;
    }
    
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,
                         String[] selectionArgs,   String sortOrder) {
        return null;
    }

    
    @Override
    public String getType( Uri uri) {
        return null;
    }

    
    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        if (values == null) {
            return null;
        }

        String res = "";
        int type = values.getAsInteger(EXTRA_TYPE);
        //定制化一些处理
        //insert做SP的查询SP的操作
        if (type == TYPE_STRING) {
            res += MultiProcessSharedPreferencesManager.getInstance(getContext()).getString(
                    values.getAsString(EXTRA_KEY), values.getAsString(EXTRA_VALUE));
        }

        return Uri.parse(MY_CONTENT_PROVIDER_URI.toString() + "/" + res);
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,
                       String[] selectionArgs) {
        if (values == null) {
            return 0;
        }

        //定制化处理
        //update做出SP的添加操作
        int type = values.getAsInteger(EXTRA_TYPE);
        if (type == TYPE_STRING) {
            MultiProcessSharedPreferencesManager.getInstance(getContext()).setString(
                    values.getAsString(EXTRA_KEY), values.getAsString(EXTRA_VALUE));
        }

        return 1;
    }
}
