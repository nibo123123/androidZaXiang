package com.example.chencj.myapplication.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import static android.view.animation.Animation.RELATIVE_TO_PARENT;

/**
 * Created by CHENCJ on 2020/11/11.
 */

public class ViewAnimatorUtils {
    /**
     * 透明度动画
     * @param mImageView
     */
    public static void alphaViewAnimation(Context context,View mImageView,int animResourceID){
        // 1.获取AlphaAnimation实例，传入起止透明度
        // 设置起止透明度，取值范围0~1（透明～不透明）
        /*float fromAlpha = 1.0f;
        float toAlpha = 0.5f;
        Animation animation = new AlphaAnimation(fromAlpha, toAlpha);

        // 2.设置动画时长，单位为毫秒
        animation.setDuration(3000);

        // 3.其他可选设置，xml都有对应的属性
        animation.setStartOffset(2000);// 设置动画开始延时，单位毫秒
        animation.setFillAfter(true);// 设置动画结束后保留最后状态
        animation.setRepeatCount(2);// 设置动画重复次数（默认为0，也就是说设置重复次数为n动画会播放n+1次），Animation.INFINITE无限播放
        animation.setRepeatMode(Animation.REVERSE);// 设置重复时播放的模式，Animation.RESTART(1)：顺序播放;Animation.REVERSE(2)：逆序播放
        // 设置为逆序播放时：首次顺序播放，第2次逆序播放，第3次顺序播放……

        // 4.开始动画
        mImageView.startAnimation(animation);*/

        // XML方式实现
        /*放在res/anim/

        <?xml version="1.0" encoding="utf-8"?>
            <alpha xmlns:android="http://schemas.android.com/apk/res/android"
                   android:fromAlpha="1.0"
                   android:toAlpha="0.5"
                   android:duration="3000"
                   android:startOffset="2000"
                   android:fillAfter="true"
                   android:repeatCount="2"
                   android:repeatMode="reverse">
                <!--fromAlpha和toAlpha取值范围0~1，0表示完全透明、1表示完全不透明-->
            </alpha>
         */
        // 通过AnimationUtils.loadAnimation(Context context, @AnimRes int id)获取Animation实例
        Animation animation = AnimationUtils.loadAnimation(context, animResourceID);
        // ImageView开始动画传入上述Animation实例
        mImageView.startAnimation(animation);
    }

    /**
     * 线性动画
     * @param context
     * @param mImageView
     * @param animResourceID
     */
    public static void translateViewAnimation(Context context,View mImageView,int animResourceID){
        // 平移动画的起始点应为View的中心点，而不是左上角
        // 1.获取TranslateAnimation实例，传入起止XY坐标（都是相对于View原位置的绝对偏移量）
        float fromXDelta = 0f;// 开始时View相对于原位置的X偏移
        float toXDelta = 200f;// 结束时View相对于原位置的X偏移
        float fromYDelta = 0f;// 开始时View相对于原位置的Y偏移
        float toYDelta = 200f;// 结束时View相对于原位置的Y偏移

        // 此构造函数坐标Type为
        // Animation.ABSOLUTE 使用绝对的尺寸
        // Animation.RELATIVE_TO_PARENT 相对父窗体 0-100%p
        // Animation.RELATIVE_TO_SELF  相对自己 0-100%
        //Animation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);


        Animation animation = new  TranslateAnimation(RELATIVE_TO_PARENT, fromXDelta, RELATIVE_TO_PARENT, 1,
                RELATIVE_TO_PARENT, fromYDelta, RELATIVE_TO_PARENT, 1);
        // 2.设置动画时长，单位为毫秒
        animation.setDuration(10000);
        animation.setFillAfter(true);//保持最后状态

        // 3.开始动画
        mImageView.startAnimation(animation);

        // XML方式实现
        /*放在res/anim/

        ABSOLUTE
        <?xml version="1.0" encoding="utf-8"?>
            <translate xmlns:android="http://schemas.android.com/apk/res/android"
                       android:fromXDelta="0"
                       android:toXDelta="400"
                       android:fromYDelta="0"
                       android:toYDelta="400"
                       android:duration="3000">

            </translate>

        <?xml version="1.0" encoding="utf-8"?>
            <translate xmlns:android="http://schemas.android.com/apk/res/android"
                       android:fromXDelta="0"
                       android:toXDelta="50%p"
                       android:fromYDelta="0"
                       android:toYDelta="200%"
                       android:duration="3000">
                <!--无后缀 = ABSOLUTE;% = RELATIVE_TO_SELF;%p = RELATIVE_TO_PARENT-->
            </translate>
         */


        //
    }

    /**
     * 旋转动画
     * @param context
     * @param mImageView
     * @param animResourceID
     */
    public static void rotateViewAnimation(Context context,View mImageView,int animResourceID){

        // 此构造函数坐标Type为
        // Animation.ABSOLUTE 使用绝对的尺寸
        // Animation.RELATIVE_TO_PARENT 相对父窗体 0-100%p
        // Animation.RELATIVE_TO_SELF  相对自己 0-100%
        //以某一点为顶点旋转fromDegrees, toDegrees的角度
        //fromDegrees > toDegrees逆时针旋转
        //fromDegrees < toDegrees顺时针旋转
        Animation animation = new RotateAnimation(0, 45);

        //Animation animation = new RotateAnimation(0, 45, mImageView.getWidth() / 2, mImageView.getHeight() / 2);

//        Animation animation = new RotateAnimation(0, 45,
//                Animation.RELATIVE_TO_PARENT, 0.25f,
//                Animation.RELATIVE_TO_PARENT, 0.25f);
        animation.setDuration(3000);
        mImageView.startAnimation(animation);
        /*
        放在res/anim/
        xml实现
        <?xml version="1.0" encoding="utf-8"?>
            <rotate xmlns:android="http://schemas.android.com/apk/res/android"
                    android:fromDegrees="0"
                    android:toDegrees="45"
                    android:pivotX="25%p"
                    android:pivotY="25%p"
                    android:duration="3000">

            </rotate>
         */
    }

    /**
     * 缩放动画
     * @param context
     * @param mImageView
     * @param animResourceID
     */
    public static void scaleViewAnimatoion(Context context,View mImageView,int animResourceID){

        // 此构造函数坐标Type为
        // Animation.ABSOLUTE 使用绝对的尺寸
        // Animation.RELATIVE_TO_PARENT 相对父窗体 0-100%p
        // Animation.RELATIVE_TO_SELF  相对自己 0-100%
        //以某一点为顶点  从fromX--》toX, fromY--》toYfromDegrees 进行缩放因子

        //一般从1开始
        // fromX > toX x方向缩小, fromY  > toYfromDegrees y方向缩小
        // fromX 《 toX x方向放大, fromY  《 toYfromDegrees y方向放大

        Animation animation = new ScaleAnimation(1, 2, 1, 2);
        //Animation animation = new ScaleAnimation(1, 2, 1, 2, mImageView.getWidth() / 2, mImageView.getHeight() / 2);
//        Animation animation = new ScaleAnimation(1, 2, 1, 2,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        mImageView.startAnimation(animation);
        /*
        放在res/anim/
        xml实现
        <?xml version="1.0" encoding="utf-8"?>
            <scale xmlns:android="http://schemas.android.com/apk/res/android"
                   android:fromXScale="1"
                   android:toXScale="2"
                   android:fromYScale="1"
                   android:toYScale="2"
                   android:pivotX="50%"
                   android:pivotY="50%"
                   android:duration="3000">

            </scale>
         */
    }

    /**
     * 混合补间动画
     * @param context
     * @param mImageView
     * @param animResourceID
     */
    public static void mixViewAnimation(Context context,View mImageView,int animResourceID) {
        /*float fromXDelta = 0f;// 开始时View相对于原位置的X偏移
        float toXDelta = 200f;// 结束时View相对于原位置的X偏移
        float fromYDelta = 0f;// 开始时View相对于原位置的Y偏移
        float toYDelta = 200f;// 结束时View相对于原位置的Y偏移

        Animation scaleAnimation = new ScaleAnimation(1, 2, 1, 2);

        Animation rotateAnimation = new RotateAnimation(0, 45);

        Animation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);

        float fromAlpha = 1.0f;
        float toAlpha = 0.5f;
        Animation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);

        //使用set进行装起来
        AnimationSet animationSet = new AnimationSet(false);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);


        animationSet.setDuration(10000);
        animationSet.setFillAfter(true);//保持最后状态

        */

        // XML方式实现
        /*放在res/anim/

      <?xml version="1.0" encoding="utf-8"?>
            <set xmlns:android="http://schemas.android.com/apk/res/android">
                <alpha
                    android:fromAlpha="1.0"
                    android:toAlpha="0.5"
                    android:duration="3000"
                    android:startOffset="2000"
                    android:fillAfter="true"
                    android:repeatCount="2"
                    android:repeatMode="reverse">
                    <!--fromAlpha和toAlpha取值范围0~1，0表示完全透明、1表示完全不透明-->
                </alpha>

                <translate
                    android:fromXDelta="0"
                    android:toXDelta="50%p"
                    android:fromYDelta="0"
                    android:toYDelta="200%"
                    android:duration="3000">
                    <!--无后缀 = ABSOLUTE;% = RELATIVE_TO_SELF;%p = RELATIVE_TO_PARENT-->
                </translate>
            </set>
         */

        Animation animation = AnimationUtils.loadAnimation(context, animResourceID);
        // 3.开始动画
        mImageView.startAnimation(animation);
    }


    /*
    属性动画
    ValueAnimator  从初始值平滑地过渡到结束值效果


     */

    public static ValueAnimator createValueAnimate(final View view, int start, int end){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取view的LayoutParams布局参数
                ViewGroup.LayoutParams params = view.getLayoutParams();
                //改变布局参数
                params.height = (int) animation.getAnimatedValue();
                params.width  = (int) animation.getAnimatedValue();
                //重新设置进去
                view.setLayoutParams(params);
            }
        });
        return valueAnimator;
    }
    
    /*
    以及 ObjectAnimator 可以直接对任意对象的任意属性进行动画操作的
    after(Animator anim)   将现有动画插入到传入的动画之后执行
    after(long delay)   将现有动画延迟指定毫秒后执行
    before(Animator anim)   将现有动画插入到传入的动画之前执行
    with(Animator anim)   将现有动画和传入的动画同时执行


     */

    public static void useObjectAnimator(Context context,View mImageView){

        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();

    }

}
