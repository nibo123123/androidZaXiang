package com.example.chencj.myapplication.video;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.chencj.myapplication.R;
import com.example.chencj.myapplication.reflect.ReflectionUtil;

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

        //xml的videoview显示时实例化，
        //

        //设置path，调用openVideo 需要mUri和mSurfacehold不为空
        //把mMediaplayer实例化
        mVideoView.setVideoPath(path);

        //创建MediaController对象
        MediaController mediaController = new MediaController(this);

        //VideoView与MediaController建立关联
        mVideoView.setMediaController(mediaController);

        //让VideoView获取焦点
        //mVideoView.requestFocus();

        final SurfaceHolder holder = mVideoView.getHolder();

        mVideoView.post(new Runnable() {
            @Override
            public void run() {
                SurfaceHolder mSurfaceHolder = (SurfaceHolder) ReflectionUtil.getFieldObj(mVideoView, VideoView.class.getName(), "mSurfaceHolder", null);
                System.out.println(holder);
                System.out.println(mSurfaceHolder);
                System.out.println("==="+(holder == mSurfaceHolder));
            }
        });


    }
}
