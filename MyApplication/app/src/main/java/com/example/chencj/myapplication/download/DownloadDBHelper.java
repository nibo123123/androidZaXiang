package com.example.chencj.myapplication.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by CHENCJ on 2020/11/18.
 */

public class DownloadDBHelper extends SQLiteOpenHelper {
    /**
     * 数据库的名字
     */
    private static final String DB_NAME = "download_info.db";

    /**
     * 创建表
     */
    private static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,startLen long,total long,processLen long)";

    /**
     * 删除表
     */
    private static final String SQL_DROP = "drop table if exists thread_info";
    
    private static final int VERSION = 1;

    private static DownloadDBHelper myDownloadDBHelper;

    public static DownloadDBHelper getInstance(Context context) {
        if (myDownloadDBHelper == null) {
            myDownloadDBHelper = new DownloadDBHelper(context);
        }
        return myDownloadDBHelper;
    }


    private DownloadDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE);
        }catch (Exception e){
            Log.e("DownloadDBHelper chencj ", "onCreate: ",e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(SQL_DROP);
            db.execSQL(SQL_CREATE);
        }catch (Exception e){
            Log.e("DownloadDBHelper chencj ", "onCreate: ",e);
        }
    }
}
