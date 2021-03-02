package com.example.chencj.myapplication.video;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.chencj.myapplication.R;

/**
 * Created by CHENCJ on 2021/2/26.
 */

public class Video1Activity extends Activity {
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_first);
        mVideoView = (VideoView)findViewById(R.id.videoview1);

        //加载指定的视频文件
        String path = Environment.getExternalStorageDirectory().getPath()+"/20210226.mp4";
        mVideoView.setVideoPath(path);

        //创建MediaController对象
        MediaController mediaController = new MediaController(this);

        //VideoView与MediaController建立关联
        mVideoView.setMediaController(mediaController);

        //让VideoView获取焦点
        mVideoView.requestFocus();
    }
}
