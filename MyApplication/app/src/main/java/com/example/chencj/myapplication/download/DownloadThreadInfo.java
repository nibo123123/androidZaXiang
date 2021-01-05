package com.example.chencj.myapplication.download;

/**
 * Created by CHENCJ on 2020/11/18.
 */

/**
 * 下载线程的信息的封装
 */
public class DownloadThreadInfo {
    private int id;         // ID
    private String url;     // 下载地址
    private long startLen;     // 开始长度
    private long total;       // 目标文件的总长度
    private long processLen;  // 完成的长度

    public DownloadThreadInfo() {
    }

    public DownloadThreadInfo(int id, String url, long startLen, long total, long processLen) {
        this.id = id;
        this.url = url;
        this.startLen = startLen;
        this.total = total;
        this.processLen = processLen;
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

    public long getStartLen() {
        return startLen;
    }

    public void setStartLen(long startLen) {
        this.startLen = startLen;
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
        return "DownloadThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", startLen=" + startLen +
                ", total=" + total +
                ", processLen=" + processLen +
                '}';
    }
}
