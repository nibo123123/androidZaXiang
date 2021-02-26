package com.example.slidephoto.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.slidephoto.R;
import com.example.slidephoto.utils.UIUtils;

/**
 * Created by CHENCJ on 2021/2/26.
 */

public class SlidePhotoView extends LinearLayout {

    private static final int DEFAULT_SIZE = 50;
    private final float blockSize;
    private final int bgSrcId;//验证的背景
    private final int progressThumbId;//seekbar的底条
    private final int progressDrawableId;//seekbar的底条
    private final int failMaxCount;//最大失败次数
    private final int slideMode;//模式  触摸 滑动
    private SlideSeekBar slideSeekBar;
    private SlideImageView slideImageView;

    private boolean down = false;//是否操作slideSeekBar来触发slideImageView的down
    private boolean move = false;//是否操作slideSeekBar来触发slideImageView的move


    public SlidePhotoView(Context context) {
        this(context,null);
    }

    public SlidePhotoView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SlidePhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //R.styleable.SlidePhotoView 是int数组
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidePhotoView);
        blockSize = typedArray.getDimensionPixelSize(R.styleable.SlidePhotoView_blockSize, UIUtils.dp2px(context,DEFAULT_SIZE));
        bgSrcId = typedArray.getResourceId(R.styleable.SlidePhotoView_bg_src, R.drawable.cat);
        progressThumbId = typedArray.getResourceId(R.styleable.SlidePhotoView_progressThumb, R.drawable.thumb);
        progressDrawableId = typedArray.getResourceId(R.styleable.SlidePhotoView_progressDrawable, R.drawable.po_seekbar);
        failMaxCount = typedArray.getInteger(R.styleable.SlidePhotoView_max_fail_count, 5);
        slideMode = typedArray.getInteger(R.styleable.SlidePhotoView_slide_mode, 1);
        typedArray.recycle();
        initData(context);
    }

    private void initData(Context context) {
        final View slidePhotoView = LayoutInflater.from(context).inflate(R.layout.slide_background_view, this,true);

        slideImageView = (SlideImageView)slidePhotoView.findViewById(R.id.slideImageViewId);
        slideImageView.setBackgroundResource(bgSrcId);

        slideSeekBar = (SlideSeekBar)slidePhotoView.findViewById(R.id.seekbar);

        slideSeekBar.setProgressDrawable(getResources().getDrawable(progressDrawableId));
        slideSeekBar.setThumb(getResources().getDrawable(progressThumbId));
        slideSeekBar.setThumbOffset(0);

        slideSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //移动改变 progress
                if(down){//触发外部操作down
                    down = false;
                    if (progress > 10) { //按下位置不正确,第一次按下时，大于10 不去响应
                        move = false;
                    } else {
                        move = true;
                        slideImageView.processDownControl(0);
                    }

                }

                if(move){
                    slideImageView.processMoveControl(progress);
                }else {
                    seekBar.setProgress(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                //轨迹touch时
                down = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //轨迹离开时
                down = false;
                if(move){
                    move = false;
                    slideImageView.processUpControl(seekBar.getProgress());
                }
            }
        });
    }
}
