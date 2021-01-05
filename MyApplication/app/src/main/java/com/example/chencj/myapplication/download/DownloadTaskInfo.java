package com.example.chencj.myapplication.download;

import java.io.Serializable;

/**
 *
 * Created by CHENCJ on 2020/11/18.
 * 下载任务信息的封装
 *
 *
 */

public class DownloadTaskInfo implements Serializable {

    private int id;             // ID
    private String url;         // 下载地址
    private String fileName;    // 文件名
    private long total;        // 文件大小
    private long processLen;        // 完成的大小

    public DownloadTaskInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProcessLen() {
        return processLen;
    }

    public void setProcessLen(long processLen) {
        this.processLen = processLen;
    }

    @Override
    public String toString() {
        return "DownloadTaskInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", total=" + total +
                ", processLen=" + processLen +
                '}';
    }
}
