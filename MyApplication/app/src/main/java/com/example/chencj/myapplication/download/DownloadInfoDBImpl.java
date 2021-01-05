package com.example.chencj.myapplication.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHENCJ on 2020/11/18.
 */

public class DownloadInfoDBImpl implements DownloadInfoDBInterface {

    private DownloadDBHelper myDownloadDBHelper;

    public DownloadInfoDBImpl(Context context) {
        this.myDownloadDBHelper =  DownloadDBHelper.getInstance(context);
    }


    @Override
    public void insertDownloadThreadInfo(DownloadThreadInfo threadInfo) {
        SQLiteDatabase db = null;
        try {
            db = myDownloadDBHelper.getWritableDatabase();
            db.execSQL("insert into thread_info(thread_id,url,startLen,total,processLen) values(?,?,?,?,?)",
                    new Object[]{threadInfo.getId(), threadInfo.getUrl(),
                            threadInfo.getStartLen(), threadInfo.getTotal(), threadInfo.getProcessLen()});
            Log.d("DownloadInfoDBImpl chencj ", "insertDownloadThreadInfo: insert success");
        }catch (Exception e){
            Log.e("DownloadInfoDBImpl chencj ", "insertDownloadThreadInfo: ",e);
        }finally {
            if(db!=null){
                db.close();
            }
        }

    }

    @Override
    public void deleteDownloadThreadInfo(String url, int thread_id) {
        SQLiteDatabase db = null;
        try {
            db = myDownloadDBHelper.getWritableDatabase();
            db.execSQL("delete from  thread_info where url = ? and thread_id= ?",
                    new Object[]{url, thread_id});
            Log.d("DownloadInfoDBImpl chencj ", "deleteDownloadThreadInfo: delete success");
        }catch (Exception e){
            Log.e("DownloadInfoDBImpl chencj ", "deleteDownloadThreadInfo: ",e);
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    @Override
    public void updateDownloadThreadInfo(String url, int thread_id, long processLen) {
        SQLiteDatabase db = null;
        try {
            db = myDownloadDBHelper.getWritableDatabase();
            //String sql = "update thread_info set processLen = "+processLen+" where url = '"+url+"' and thread_id = "+thread_id+";";
            db.execSQL("update thread_info set processLen = ?  where url = ? and thread_id = ?",
                    new Object[]{processLen, url, thread_id});
            Log.d("DownloadInfoDBImpl chencj ", "updateDownloadThreadInfo: update success");
        }catch (Exception e){
            Log.e("DownloadInfoDBImpl chencj ", "updateDownloadThreadInfo: ",e);
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    @Override
    public List<DownloadThreadInfo> getDownloadThreadInfo(String url) {
        List<DownloadThreadInfo> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = myDownloadDBHelper.getWritableDatabase();
            String sql = "select * from thread_info where url = ? ORDER BY _id DESC";
            Cursor cursor = db.rawQuery(sql, new String[]{url});
            if(cursor!=null){
                while (cursor.moveToNext()){
                    DownloadThreadInfo thread = new DownloadThreadInfo();
                    thread.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
                    thread.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                    thread.setStartLen(cursor.getLong(cursor.getColumnIndex("startLen")));
                    thread.setTotal(cursor.getLong(cursor.getColumnIndex("total")));
                    thread.setProcessLen(cursor.getLong(cursor.getColumnIndex("processLen")));
                    list.add(thread);
                }
            }
        } catch (Exception e){
            Log.e("DownloadInfoDBImpl chencj ", "getDownloadThreadInfo: ",e);
        }finally {
            if(db!=null){
                db.close();
            }
        }
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = null;
        boolean result = false;
        try {
            db = myDownloadDBHelper.getWritableDatabase();
            String sql = "select count(*) from thread_info where url = ? and thread_id = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{url,thread_id+""});
            if(cursor!=null && cursor.moveToFirst()){
                result = true;
            }
        } catch (Exception e){
            Log.e("DownloadInfoDBImpl chencj ", "isExists: ",e);
        }finally {
            if(db!=null){
                db.close();
            }
        }
        return result;
    }
}
