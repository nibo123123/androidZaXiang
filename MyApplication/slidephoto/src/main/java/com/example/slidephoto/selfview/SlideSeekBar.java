package com.example.slidephoto.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.example.slidephoto.R;
import com.example.slidephoto.utils.UIUtils;

/**
 * Created by CHENCJ on 2021/2/25.
 */

public class SlideSeekBar extends SeekBar {
    private Paint textPaint;

    public SlideSeekBar(Context context) {
        this(context,null);
    }

    public SlideSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.SlideSeekBarStyle);
    }

    public SlideSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        textPaint = new Paint();//画笔
        textPaint.setTextAlign(Paint.Align.CENTER);
        int textSize = UIUtils.dp2px(context, 14);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#545454"));//画笔颜色
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int baseLineY = (int) (getHeight() / 2 - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText("向右滑动滑块完成拼图", getWidth() / 2, baseLineY, textPaint);
    }
}
