package com.example.chencj.myapplication.download;

import java.util.List;

/**
 * Created by CHENCJ on 2020/11/18.
 * 存储下载信息的数据库接口
 */

public interface DownloadInfoDBInterface {
    /**
     * 插入下载线程信息
     *
     * @param threadInfo 线程信息
     */
    void insertDownloadThreadInfo(DownloadThreadInfo threadInfo);

    /**
     * 删除下载线程信息
     *
     * @param url       地址
     * @param thread_id id
     */
    void deleteDownloadThreadInfo(String url, int thread_id);

    /**
     *
     * 更新下载线程信息
     *
     * @param url       地址
     * @param thread_id id
     * @param processLen  完成进度
     */
    void updateDownloadThreadInfo(String url, int thread_id, long processLen);

    /**
     * 查询文件的线程信息
     *
     * @param url 地址
     * @return 信息
     */
    List<DownloadThreadInfo> getDownloadThreadInfo(String url);

    /**
     * 判断是否存在
     *
     * @param url       地址
     * @param thread_id id
     * @return 是否存在
     */
    boolean isExists(String url, int thread_id);
}
