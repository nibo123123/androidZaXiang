package com.example.chencj.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CHENCJ on 2020/11/4.
 */

public class CustScratchView extends View {
    private Bitmap mGrayBitmap;
    private Paint mGrayPaint;

    public CustScratchView(Context context) {
        this(context,null);
    }

    public CustScratchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustScratchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mGrayBitmap = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mGrayBitmap);
        mGrayPaint = new Paint(Color.GRAY);
        canvas.drawColor(Color.GRAY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthDefault = 400;
        int heightDefault = 400;

        //说明高宽都是wrap_contetn
        if(getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(widthDefault,heightDefault);
        }else if(getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(widthDefault,heightSize);
        }else if(getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(widthSize,heightDefault);
        }



    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mGrayBitmap,200,200,null);
    }
}
