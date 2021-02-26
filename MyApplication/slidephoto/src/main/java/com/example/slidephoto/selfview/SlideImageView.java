package com.example.slidephoto.selfview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.slidephoto.common.PositionInfo;
import com.example.slidephoto.strategy.DefaultSlideStrategy;
import com.example.slidephoto.strategy.SlideStrategy;
import com.example.slidephoto.utils.StringUtils;

/**
 * Created by CHENCJ on 2021/2/25.
 */

public class SlideImageView extends ImageView {
    private static final int PARTIAL_FAIL = 8;
    //设置滑动的一些策略，画笔，形状等
    private SlideStrategy mSlideStrategy;
    //缺影的画笔
    private Paint shadowPaint;
    //滑动的画笔
    private Paint bitmapPaint;

    private PositionInfo shadowInfo;//设置缺影的区域的起点
    private int blockSize = 50;//缺影的大小，以正方形边长区域
    private PositionInfo slideInfo;//设置滑动的区域的起点
    private Path blockShapePath;//设置区域的真正轨迹区域
    private Bitmap slideBlockBitmap;//滑动区域的bitmap
    private int mState = IDLE_STATUS;

    private static final int DOWN_STATUS = 1;//点击在滑动区域开始
    private static final int MOVE_STATUS = 2;//点击在滑动区域移动
    private static final int UP_STATUS = 3;//点击在滑动区域离开

    private static final int IDLE_STATUS = 0;//空闲状态

    private static final int SUCCESS_STATUS = -1;//成功匹配
    private static final int FAILED_STATUS = -2;//失败匹配

    interface VerifyCallback{
        void successCallback();
        void failCallback();
    }

    private VerifyCallback verifyCallback;

    public void setVerifyCallback(VerifyCallback verifyCallback) {
        this.verifyCallback = verifyCallback;
    }

    public SlideImageView(Context context) {
        this(context,null);
    }

    public SlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        mSlideStrategy = new DefaultSlideStrategy(context);
        shadowPaint = mSlideStrategy.getBlockShadowPaint();
        bitmapPaint = mSlideStrategy.getBlockBitmapPaint();
        //为paint设置分层使用cpu画
        setLayerType(View.LAYER_TYPE_SOFTWARE, bitmapPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDrawData();

        if (mState != SUCCESS_STATUS) {
            //不是成功的状态就画出 缺影区域
            canvas.drawPath(blockShapePath, shadowPaint);
        }
        if (mState != UP_STATUS || mState != SUCCESS_STATUS) {
            //不是成功的状态或者不是Up状态都要画出
            //UP状态为了去检验 是否匹配
            canvas.drawBitmap(slideBlockBitmap, slideInfo.left, slideInfo.top, bitmapPaint);
        }
    }

    private void initDrawData() {
        if (shadowInfo == null) {
            shadowInfo = mSlideStrategy.getBlockPositionInfo(getWidth(), getHeight(), blockSize);
            if (true/*mMode == Captcha.MODE_BAR*/) {
                slideInfo = new PositionInfo(0, shadowInfo.top);
            } else {
                slideInfo = mSlideStrategy.getPositionInfoForSwipeBlock(getWidth(), getHeight(), blockSize);
            }
        }
        if (blockShapePath == null) {
            blockShapePath = mSlideStrategy.getBlockShape(blockSize);
            blockShapePath.offset(shadowInfo.left, shadowInfo.top);
        }
        if (slideBlockBitmap == null) {
            slideBlockBitmap = createBlockBitmap();
        }
    }

    private Bitmap createBlockBitmap() {
        Bitmap result = null;
        //得到整个当前view大小的bitmap
        Bitmap tempBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        //设置到画布上
        Canvas canvas = new Canvas(tempBitmap);
        Drawable drawable = getBackground();//得到view的图片
        drawable.setBounds(0,0,getWidth(),getHeight());//截取getWidth() getHeight()的区域
        //区域轨迹 blockShapePath
        //从canvas得到对应的path的区域，只会在canvas这个path区域显示bitmap
        canvas.clipPath(blockShapePath);
        //把drawable画在画布上
        drawable.draw(canvas);

        //设置边界
        mSlideStrategy.decorateSwipeBlockBitmap(canvas,blockShapePath);


        result = Bitmap.createBitmap(tempBitmap,
                shadowInfo.left, shadowInfo.top,
                blockSize, blockSize);
        //回收bitmap
        tempBitmap.recycle();
        //平移到缺影的起点位置
        //blockShapePath.offset(shadowInfo.left,shadowInfo.top);

        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //ACTION_DOWN==>DOWN_STATUS
        Log.d("SlideImageView chencj ", "onTouchEvent: event="+event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                processDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                processMove(event);
                break;
            case MotionEvent.ACTION_UP:
                processUp(event);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void processUp(MotionEvent event) {
        mState = UP_STATUS;
        invalidate();
    }

    private void processMove(MotionEvent event) {
        mState = MOVE_STATUS;
        invalidate();
    }

    private void processDown(MotionEvent event) {
        mState = DOWN_STATUS;

        invalidate();
    }

    /**
     * 外部控制down
     * @param progress 当前的距离
     */

    public void processDownControl(int progress){
        Log.d("SlideImageView chencj ", "processDownControl: progress="+progress);
        mState = DOWN_STATUS;
        /**
         * seekbar的process是从0-100
         * 移动长度 getWidth() - blockSize
         * 当前移动长度
         */
        slideInfo.left = (int) (progress / 100f * (getWidth() - blockSize));
        invalidate();
    }

    /**
     * 外部控制move
     * @param progress 当前的距离
     */
    public void processMoveControl(int progress){
        Log.d("SlideImageView chencj ", "processMoveControl: progress="+progress);
        mState = MOVE_STATUS;
        /**
         * seekbar的process是从0-100
         * 移动长度 getWidth() - blockSize
         * 当前移动长度
         */
        slideInfo.left = (int) (progress / 100f * (getWidth() - blockSize));
        invalidate();
    }

    /**
     * 外部控制up
     * @param progress 当前的距离
     */
    public void processUpControl(int progress){
        Log.d("SlideImageView chencj ", "processUpControl: progress="+progress);
        mState = UP_STATUS;

        //检查是否匹配
        checkAccess();
        invalidate();
    }

    private void checkAccess() {
        //对于外部控制 只需要检测缺影和滑块的左右距离是否一直 上下一直相同
        //对于触摸控制，需要左右及上下检测
        Log.d("SlideImageView chencj ", "checkAccess: ");
        if(Math.abs(slideInfo.left - shadowInfo.left) < PARTIAL_FAIL
                && Math.abs(slideInfo.top - shadowInfo.top) < PARTIAL_FAIL){
            //成功
            success();
            if(StringUtils.isNotNull(verifyCallback)){
                verifyCallback.successCallback();
            }
        }else {
            //失败
            fail();
            if(StringUtils.isNotNull(verifyCallback)){
                verifyCallback.failCallback();
            }
        }

    }

    private void fail() {
        mState = FAILED_STATUS;
        invalidate();
    }

    private void success() {
        mState = SUCCESS_STATUS;

        invalidate();
    }

}
