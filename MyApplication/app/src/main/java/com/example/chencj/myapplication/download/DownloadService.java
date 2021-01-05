package com.example.chencj.myapplication.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CHENCJ on 2020/11/18.
 */

public class DownloadService extends Service {

    public static final String FILE_INFO = "file_info";


    /**
     * action
     */
    private static final int MSG_INIT = 0;                          //初始化
    public static final String ACTION_START = "ACTION_START";       //开始下载
    public static final String ACTION_PAUSE = "ACTION_PAUSE";       //暂停下载
    public static final String ACTION_FINISHED = "ACTION_FINISHED"; //结束下载
    public static final String ACTION_UPDATE = "ACTION_UPDATE";     //更新UI

    /**
     * 需要增加权限，并且动态去获取
     * 下载路径 这里保存在SD卡里面
     */
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloads/";
    /**
     * 执行下载的那个任务 里面有线程 网络请求 数据保存
     */
    private DownloadTask mDownloadTask;

    @Override
    public void onCreate() {
        super.onCreate();
        
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        processIntent(intent);
        
        
        return super.onStartCommand(intent, flags, startId);
    }

    private void processIntent(Intent intent) {
        // 获得Activity传来的参数
        if (ACTION_START.equals(intent.getAction())) {
            DownloadTaskInfo downloadTaskInfo = (DownloadTaskInfo) intent.getSerializableExtra(FILE_INFO);
            new InitThread(downloadTaskInfo).start();
        } else if (ACTION_PAUSE.equals(intent.getAction())) {
            DownloadTaskInfo downloadTaskInfo = (DownloadTaskInfo) intent.getSerializableExtra(FILE_INFO);
            if (mDownloadTask != null) {
                mDownloadTask.isPause = true;
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_INIT) {
                DownloadTaskInfo downloadTaskInfo = (DownloadTaskInfo) msg.obj;
                // 启动下载任务
                mDownloadTask = new DownloadTask(DownloadService.this, downloadTaskInfo);
                mDownloadTask.startDownTask();
            }
        }
    };

    /**
     * 初始化子线程 这一步的作用是 获取下载目标的信息
     */
    private class InitThread extends Thread {

        private DownloadTaskInfo downloadTaskInfo;

        public InitThread(DownloadTaskInfo downloadTaskInfo) {
            this.downloadTaskInfo = downloadTaskInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                //连接网络文件
                URL url = new URL(downloadTaskInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;

                //获取目标文件长度
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    length = conn.getContentLength();
                }

                if (length < 0) {
                    return;
                }

                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir, downloadTaskInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");

                //设置本地文件长度
                raf.setLength(length);
                downloadTaskInfo.setTotal(length);

                // 发消息
                mHandler.obtainMessage(MSG_INIT, downloadTaskInfo).sendToTarget();

            } catch (Exception e) {
                Log.e("InitThread chencj ", "run: ", e);
            } finally {
                try {
                    if (conn != null && raf != null) {
                        raf.close();
                        conn.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("InitThread chencj ", "run: ", e);
                }
            }
        }
    }

}
