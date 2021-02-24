package com.example.chencj.myapplication.lunbo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chencj.myapplication.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2021/2/24.
 */

public class LunBoPhotoActivity  extends AppCompatActivity implements OnBannerListener {

    private Banner banner;
    private ArrayList<String> list_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbo_main);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        banner = (Banner)findViewById(R.id.banner);
    }

    private void initData() {
        setBanner();//设置轮播图
    }

    private void initListener() {

    }

    /**
     * 设置轮播图
     */
    private void setBanner() {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //设置图片数据
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/2a919def19fc47e3aa0d75d8c227ab1b.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/d027d1efc0564c44bb979ba0bd21f560.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/bbb930d66e5a48baa8d3c143544d7631.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/fb1721b8c9be4da9949fcdd26fc902a2.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/08b58dde9b284638b44e2d03c4cb9acf.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/d3caeb6129ee43df87f5c1e1058d96fc.jpg");
        list_path.add("https://sami-1256315447.picgz.myqcloud.com/article/201908/9fd01c4add07473db31ba850f20a7232.jpg");
        list_path.add("http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg");

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new ImgLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(this, BigImgActivity.class);
        intent.putStringArrayListExtra("imgData", list_path);
        intent.putExtra("clickPosition", position);
        startActivity(intent);
    }

    //自定义的图片加载器
    private class ImgLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
