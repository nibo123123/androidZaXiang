package com.example.slidephoto.strategy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.slidephoto.common.PositionInfo;

import java.util.Random;

/**
 * Created by CHENCJ on 2021/2/25.
 */

public class DefaultSlideStrategy extends SlideStrategy {
    public DefaultSlideStrategy(Context ctx) {
        super(ctx);
    }

    @Override
    public Path getBlockShape(int blockSize) {
        int gap = (int) (blockSize/5f);
        Path path = new Path();
        path.moveTo(0, gap);
        path.rLineTo(blockSize/2.5f, 0);
        path.rLineTo(0, -gap);
        path.rLineTo(gap, 0);
        path.rLineTo(0, gap);
        path.rLineTo(2 * gap, 0);
        path.rLineTo(0, 4 * gap);
        path.rLineTo(-5 * gap, 0);
        path.rLineTo(0, -1.5f * gap);
        path.rLineTo(gap, 0);
        path.rLineTo(0, -gap);
        path.rLineTo(-gap, 0);
        path.close();
        return path;
    }

    @Override
    public PositionInfo getBlockPositionInfo(int width, int height, int blockSize) {
        Random random = new Random();
        int left = random.nextInt(width - blockSize +1);
        //避免滑动图片与缺影交织到一起
        if (left < blockSize) {
            left = blockSize;
        }
        int top = random.nextInt(height - blockSize +1);
        if (top < 0) {
            top = 0;
        }
        return new PositionInfo(left, top);
    }

    /**
     * 为了设置滑块的位置，不需要seekbar
     * @param width  picture width
     * @param height picture height
     * @param blockSize
     * @return
     */
    @Override
    public PositionInfo getPositionInfoForSwipeBlock(int width, int height, int blockSize) {
        Random random = new Random();
        int left = random.nextInt(width - blockSize+1);
        int top = random.nextInt(height - blockSize+1);
        if (top < 0) {
            top = 0;
        }
        return new PositionInfo(left, top);
    }

    @Override
    public Paint getBlockShadowPaint() {
        Paint shadowPaint = new Paint();
        shadowPaint.setColor(Color.parseColor("#000000"));//全白 ffffff全黑
        shadowPaint.setAlpha(255);//透明度
        return shadowPaint;
    }

    @Override
    public Paint getBlockBitmapPaint() {
        Paint paint = new Paint();
        return paint;
    }

    @Override
    public void decorateSwipeBlockBitmap(Canvas canvas, Path shape) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{20,20},10));
        Path path = new Path(shape);
        canvas.drawPath(path,paint);
    }
}
