package com.example.chencj.myapplication.download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chencj.myapplication.App;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by CHENCJ on 2020/11/18.
 */

public class DownloadTask {
    private Context mContext = null;
    private DownloadTaskInfo mDownloadTaskInfo = null;
    private DownloadInfoDBImpl mDownloadInfoDBImpl = null;
    private long processLen = 0;
    public boolean isPause = false;

    /**
     * 构造函数
     *
     * @param mContext  上下文
     * @param mDownloadTaskInfo 下载详情
     */
    public DownloadTask(Context mContext, DownloadTaskInfo mDownloadTaskInfo) {
        this.mContext = mContext;
        this.mDownloadTaskInfo = mDownloadTaskInfo;
        mDownloadInfoDBImpl = new DownloadInfoDBImpl(mContext);
    }

    DownloadThreadInfo info;

    /**
     * 开始任务 开启一个子线程去执行任务
     */
    public void startDownTask() {
        // 本地数据库获取到所有的下载信息
        List<DownloadThreadInfo> threadInfos = mDownloadInfoDBImpl.getDownloadThreadInfo(mDownloadTaskInfo.getUrl());

        if (threadInfos.size() == 0) {
            info = new DownloadThreadInfo(0, mDownloadTaskInfo.getUrl(), 0, mDownloadTaskInfo.getTotal(), 0);
        } else {
            info = threadInfos.get(0);
        }

        App.mThreadPool.execute(downloadRunnable);
        /*Thread a = new DownloadThread(info);
        a.start();*/
    }

    private Runnable downloadRunnable = new Runnable() {
        @Override
        public void run() {
            //如果数据库中，不存在记录就要插入数据
            if (!mDownloadInfoDBImpl.isExists(info.getUrl(), info.getId())) {
                mDownloadInfoDBImpl.insertDownloadThreadInfo(info);
            }

            //创建网络访问链接
            HttpURLConnection connection = null;
            //实现断点下载的文件
            RandomAccessFile raf = null;
            InputStream is = null;


            try {
                //包装成URL
                URL url = new URL(info.getUrl());
                //构建网络访问连接器
                connection = (HttpURLConnection) url.openConnection();
                //设置链接超时
                connection.setConnectTimeout(3000);
                //get方式请求
                connection.setRequestMethod("GET");

                //设置下载位置
                long start = info.getStartLen() + info.getProcessLen();
                Log.d("DownloadThread chencj ", "run:开始下载的位置 start = "+start);

                // 断点下载，从指定位置 继续下载

                connection.setRequestProperty("Range", "bytes=" + start + "-" + info.getTotal());

                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mDownloadTaskInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                //定位到指定的位置，继续操作
                raf.seek(start);

                //设置广播
                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                //从上次停止的地方继续下载
                processLen += info.getProcessLen();
                Log.d("DownloadThread chencj ", "run: 上次下载的位置：processLen="+processLen);

                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    is = connection.getInputStream();
                    byte[] buffer = new byte[4096];
                    int len = -1;
                    long time = System.currentTimeMillis();

                    // TODO 保存下载的数据
                    while ((len = is.read(buffer)) != -1) {

                        Log.d("DownloadThread chencj ", "run: 一次数据读写 ");

                        //下载暂停时，保存进度
                        if (isPause) {
                            Log.d("DownloadThread chencj ", "run: 进度为：" + processLen);
                            mDownloadInfoDBImpl.updateDownloadThreadInfo(mDownloadTaskInfo.getUrl(), info.getId(), processLen);
                            // TODO 暂停之后 直接返回 线程结束
                            return;
                        }

                        raf.write(buffer, 0, len);
                        processLen += len;

                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            intent.putExtra("processLen", processLen * 100 / mDownloadTaskInfo.getTotal());
                            Log.d("DownloadThread chencj ", "run:这里发送广播了" + processLen + " -- " + mDownloadTaskInfo.getTotal());
                            mContext.sendBroadcast(intent);
                        }
                    }

                    intent.putExtra("processLen", (long) 100);
                    mContext.sendBroadcast(intent);
                    mDownloadInfoDBImpl.deleteDownloadThreadInfo(mDownloadTaskInfo.getUrl(), mDownloadTaskInfo.getId());

                }
            } catch (Exception e) {
                Log.e("DownloadThread chencj ", "run: ", e);
            } finally {
                try {
                    if (is != null)
                        is.close();
                    if (raf != null)
                        raf.close();
                    if (connection != null)
                        connection.disconnect();
                } catch (IOException e) {
                    Log.e("DownloadThread chencj ", "run: ", e);
                }
            }
        }
    };

    /**
     * 这个是下载的线程
     */
    private class DownloadThread extends Thread {

        private DownloadThreadInfo threadInfo = null;

        public DownloadThread(DownloadThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            //如果数据库中，不存在记录就要插入数据
            if (!mDownloadInfoDBImpl.isExists(threadInfo.getUrl(), threadInfo.getId())) {
                mDownloadInfoDBImpl.insertDownloadThreadInfo(threadInfo);
            }

            //创建网络访问链接
            HttpURLConnection connection = null;
            //实现断点下载的文件
            RandomAccessFile raf = null;
            InputStream is = null;


            try {
                //包装成URL
                URL url = new URL(threadInfo.getUrl());
                //构建网络访问连接器
                connection = (HttpURLConnection) url.openConnection();
                //设置链接超时
                connection.setConnectTimeout(3000);
                //get方式请求
                connection.setRequestMethod("GET");

                //设置下载位置
                long start = threadInfo.getStartLen() + threadInfo.getProcessLen();
                Log.d("DownloadThread chencj ", "run:开始下载的位置 start = "+start);

                // 断点下载，从指定位置 继续下载

                connection.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getTotal());

                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mDownloadTaskInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                //定位到指定的位置，继续操作
                raf.seek(start);

                //设置广播
                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                //从上次停止的地方继续下载
                processLen += threadInfo.getProcessLen();
                Log.d("DownloadThread chencj ", "run: 上次下载的位置：processLen="+processLen);

                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    is = connection.getInputStream();
                    byte[] buffer = new byte[4096];
                    int len = -1;
                    long time = System.currentTimeMillis();

                    // TODO 保存下载的数据
                    while ((len = is.read(buffer)) != -1) {

                        Log.d("DownloadThread chencj ", "run: 一次数据读写 ");

                        //下载暂停时，保存进度
                        if (isPause) {
                            Log.d("DownloadThread chencj ", "run: 进度为：" + processLen);
                            mDownloadInfoDBImpl.updateDownloadThreadInfo(mDownloadTaskInfo.getUrl(), threadInfo.getId(), processLen);
                            // TODO 暂停之后 直接返回 线程结束
                            return;
                        }

                        raf.write(buffer, 0, len);
                        processLen += len;

                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            intent.putExtra("processLen", processLen * 100 / mDownloadTaskInfo.getTotal());
                            Log.d("DownloadThread chencj ", "run:这里发送广播了" + processLen + " -- " + mDownloadTaskInfo.getTotal());
                            mContext.sendBroadcast(intent);
                        }
                    }

                    intent.putExtra("processLen", (long) 100);
                    mContext.sendBroadcast(intent);
                    mDownloadInfoDBImpl.deleteDownloadThreadInfo(mDownloadTaskInfo.getUrl(), mDownloadTaskInfo.getId());

                }
            } catch (Exception e) {
                Log.e("DownloadThread chencj ", "run: ", e);
            } finally {
                try {
                    if (is != null)
                        is.close();
                    if (raf != null)
                        raf.close();
                    if (connection != null)
                        connection.disconnect();
                } catch (IOException e) {
                    Log.e("DownloadThread chencj ", "run: ", e);
                }
            }
        }
    }

}
