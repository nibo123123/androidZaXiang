package com.example.chencj.myapplication.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by CHENCJ on 2021/3/10.
 */

public class SelfBezierView extends View {
    private Paint mPaint;
    private float radius = 300;
    private static final int SCALE = 1000;
    private Path mPath;

    class Point{
        private float x;
        private float y;

        public void clear() {
            x = 0;
            y = 0;
        }
    }
    Point mACenterPoint = new Point();
    Point mBCenterPoint = new Point();

    Point mDownPoint = new Point();
    Point mMovePoint = new Point();
    Point mUpPoint = new Point();

    public SelfBezierView(Context context) {
        super(context);
        initView(context);
    }

    public SelfBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SelfBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        /*
        可以画几何图形，文本和bitmap
        Paint.Style.FILL：填充内部
        Paint.Style.FILL_AND_STROKE  ：填充内部和描边
        Paint.Style.STROKE  ：描边
         */
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(0,0,0));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mACenterPoint.x = displayMetrics.widthPixels / 2;
        mACenterPoint.y = displayMetrics.heightPixels / 2;

        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("SelfBezierView chencj ", "onTouchEvent: event="+event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownPoint.x = event.getX();
                mDownPoint.y = event.getY();
                checkPoint(mDownPoint);
                break;
            case MotionEvent.ACTION_MOVE:
                mMovePoint.x = event.getX();
                mMovePoint.y = event.getY();
                checkPoint(mMovePoint);
                break;
            case MotionEvent.ACTION_UP:
                clearPoint();
                break;
        }
        invalidate();
        return true;
    }

    private void clearPoint() {
        if(mDownPoint!=null){
            mDownPoint.clear();
        }
        if(mMovePoint!=null){
            mMovePoint.clear();
        }
    }

    private void checkPoint(Point point){
        if(point!=null){
            if(point.x < radius){
                point.x = radius;
            }
            if(point.y < radius){
                point.y = radius;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("SelfBezierView chencj ", "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("SelfBezierView chencj ", "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("SelfBezierView chencj ", "onDraw: ");
        canvas.drawCircle(mACenterPoint.x,mACenterPoint.y,radius,mPaint);

        if(mMovePoint.x > radius){

            float dx = mDownPoint.x - mMovePoint.x;
            float dy = mDownPoint.y - mMovePoint.y;

            if(dx == 0){
                dx = 0.001f;
            }
            //两个圆心的距离
            double distance = Math.sqrt((dx * dx + dy * dy));
            //两个圆心相对x轴的tan
            double tan = dy/dx;
            //夹角的弧度值
            double atan = Math.atan(tan);

            Log.d("SelfBezierView chencj ", "onDraw: tan="+tan +",atan="+atan);
            //在移动的时候，down圆的半径缩小
            float scaleRaduis = (float) (radius * (distance/SCALE>1?0:(1-distance/SCALE)));
            if(scaleRaduis<=5)scaleRaduis=5;
            //down圆上的点1
            Point point = new Point();
            point.y = (float) (mDownPoint.y + scaleRaduis*Math.sin(Math.PI/2 + atan));
            point.x = (float) (mDownPoint.x + scaleRaduis*Math.cos(Math.PI/2 + atan));
            mPaint.setColor(Color.rgb(255,0,0));
            canvas.drawCircle(point.x,point.y,5,mPaint);
            //down圆上的点2
            Point point1 = new Point();
            point1.y = (float) (mDownPoint.y + scaleRaduis*Math.sin(-Math.PI/2 + atan));
            point1.x = (float) (mDownPoint.x + scaleRaduis*Math.cos(-Math.PI/2 + atan));
            mPaint.setColor(Color.rgb(0,255,0));
            canvas.drawCircle(point1.x,point1.y,5,mPaint);

            //画出移动圆形
            mPaint.setColor(Color.rgb(0,0,0));
            canvas.drawCircle(mMovePoint.x,mMovePoint.y,radius,mPaint);
            //画出down⚪
            canvas.drawCircle(mDownPoint.x,mDownPoint.y,scaleRaduis,mPaint);

            //移动圆上的点1
            Point point2 = new Point();
            point2.y = (float) (mMovePoint.y + radius*Math.sin(Math.PI/2 + atan));
            point2.x = (float) (mMovePoint.x + radius*Math.cos(Math.PI/2 + atan));
            mPaint.setColor(Color.rgb(255,0,0));
            canvas.drawCircle(point2.x,point2.y,5,mPaint);

            //移动圆上的点2
            Point point3 = new Point();
            point3.y = (float) (mMovePoint.y + radius*Math.sin(-Math.PI/2 + atan));
            point3.x = (float) (mMovePoint.x + radius*Math.cos(-Math.PI/2 + atan));
            mPaint.setColor(Color.rgb(0,255,0));
            canvas.drawCircle(point3.x,point3.y,5,mPaint);


            //构建quadTo的第三个点
            Point point4 = new Point();
            point4.x = (mDownPoint.x+mMovePoint.x)/2;
            point4.y = (mDownPoint.y+mMovePoint.y)/2;

            mPaint.setColor(Color.rgb(0,0,0));
            mPath.reset();
            /*
            0 <--  1
            |     |
            2 --> 3

             */
            mPath.moveTo(point.x,point.y);
            mPath.quadTo(point4.x,point4.y,point2.x,point2.y);
            mPath.lineTo(point3.x,point3.y);
            //设置起点（point3），设置终点（point1）
            //控制点为变化（point4）
            mPath.quadTo(point4.x,point4.y,point1.x,point1.y);
            mPath.lineTo(point.x,point.y);


            canvas.drawPath(mPath,mPaint);
        }

        if(mDownPoint.x >= radius ){

        }
    }
}
