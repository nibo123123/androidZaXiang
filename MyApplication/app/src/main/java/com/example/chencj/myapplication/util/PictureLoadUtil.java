package com.example.chencj.myapplication.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by CHENCJ on 2020/11/11.
 *
 * 加入
// https://mvnrepository.com/artifact/com.github.bumptech.glide/glide
 compile group: 'com.github.bumptech.glide', name: 'glide', version: '3.7.0'



 // https://mvnrepository.com/artifact/com.squareup.picasso/picasso
 compile group: 'com.squareup.picasso', name: 'picasso', version: '2.5.2'
 // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
 //    compile group: 'com.squareup.okhttp3', name: 'okhttp', version: '3.8.0'
 // https://mvnrepository.com/artifact/com.squareup.okhttp/okhttp
 compile group: 'com.squareup.okhttp', name: 'okhttp', version: '2.7.5'


 并加入访问网络的权限
 <uses-permission android:name="android.permission.INTERNET"/>
 *
 *
 */

public class PictureLoadUtil {


    private void initGlide(Context context,View v) {
        ImageView glide_iv = (ImageView) v;
        String url = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";
        Glide.with(context).load(url).into(glide_iv);
    }

    private void initPicasso(Context context,View v) {
        ImageView pi = (ImageView)v;
        String url = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";
        Picasso.with(context).load(url).into(pi);
    }
}
