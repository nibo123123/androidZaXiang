package com.example.chencj.myapplication.surfacefling;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by CHENCJ on 2021/1/8.
 */

public class GifSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder holder;//Surface的持有者

    private String path = "test.gif";

    private Movie movie;//播放gif的承载者

    // 缩放系数

    private float zoom = 2;

    // 执行 gif动画
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {

        @Override

        public void run() {
            // gif动画 是由一帧 的图片组成，实现 gif动画就是将 一帧帧的图 画出来，
            // 首先获取画布
            if(holder!=null) {
                Canvas canvas = holder.lockCanvas();
                // 保存当前画布状态（此处保存画布状态 是为了保证 不影响下一帧的 缩放---下方 有 恢复状态）
                if(canvas == null)return;
                setZOrderMediaOverlay(true);
                canvas.save();
                canvas.scale(zoom, zoom);
                //设置画布
                movie.draw(canvas, 0, 0);
                //逐帧绘制图片
                //这里使用时间戳 与总帧数 求余操作，这样 随着时间的推移计算出该播放哪一帧
                movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
                // 恢复之前保存的状态
                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                // 循环执行
                handler.postDelayed(runnable, 1000);
            }
        }

    };

    public GifSurfaceView(Context context) {
        super(context);
        initData();
    }

    public GifSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public GifSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {

        holder = getHolder();

        holder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    /**

     *测量组件，设置 组件的宽高

     *之所有 将Moive 的初始化放在这里是因为surfaceView再 默认情况下是填充满 父组件的

     *设置SurfaceView的宽高 和gif宽高保持一致

     */

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // surfaceView 默认情况下 填充满 父组件
        //加载gif图片
        try {
            InputStream open = getContext().getAssets().open(path);
            // 使用影片对象 处理gif图片
            movie = Movie.decodeStream(open);
            // 获取 move对象的宽高（实际为gif 的宽高）
            int width = movie.width();
            int height = movie.height();
            // 设置surfaceView组件的宽高 使其保持 和 gif图的宽高一致
            setMeasuredDimension((int) (width * zoom), (int) (height * zoom));
        } catch (IOException e) {
            Log.e("GifSurfaceView chencj ", "onMeasure: ", e);
        }

    }
    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    // 开始执行gif动画

    public void start() {
        handler.post(runnable);
    }

    // 停止执行gif动画
    public void stop() {
        handler.removeCallbacks(runnable);
    }
}
