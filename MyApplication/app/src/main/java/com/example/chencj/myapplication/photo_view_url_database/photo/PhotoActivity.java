package com.example.chencj.myapplication.photo_view_url_database.photo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chencj.myapplication.App;
import com.example.chencj.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by CHENCJ on 2021/2/23.
 */

public class PhotoActivity extends Activity {
    private static final int NET_SUCCESS = 1;
    private ImageView ivIcon;
    private EditText etUrl;

    Handler mHandler =  new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == NET_SUCCESS){

                Bitmap bit = (Bitmap) msg.obj;
                Toast.makeText(PhotoActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                ivIcon.setImageBitmap(bit);
            }

        }
    };
    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_photo);
        initDataView();
    }

    private void initDataView() {
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        etUrl = (EditText) findViewById(R.id.et_url);
        Button btn = (Button) findViewById(R.id.btn_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url=etUrl.getText().toString();
                switch (type){
                    case 0:
                        App.mThreadPool.execute(new Runnable() {
                            @Override
                            public void run() {

                                Bitmap bit=getImageFromOriginalNet(url);
                                mHandler.sendMessage(mHandler.obtainMessage(NET_SUCCESS,bit));
                            }
                        });
                        break;
                    case 1:
                        getImageFromPhotoFrameworkNet(PhotoActivity.this,url,ivIcon);
                        break;
                }




            }
        });
    }

    public static Bitmap getImageFromOriginalNet(String url){
        Bitmap bitmap = null;//流解析成位图
        InputStream is = null;//信息流
        long l = 0;//时间
        try {

            URL mUrl=new URL(url);//创建URL对象
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();//使用url获取网络连接对象
            conn.setRequestMethod("GET");//请求方式 get

            conn.setConnectTimeout(10000);  //设置连接超时时间
            conn.setReadTimeout(5000);		//设置读取超时时间
            conn.connect();//发起网络请求
            int responseCode = conn.getResponseCode();
            l = SystemClock.currentThreadTimeMillis();
            if(responseCode==200){
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap.Config config = Bitmap.Config.ARGB_8888;// : Bitmap.Config.RGB_565;;
                //在解析图片的流  首先使用inJustDecodeBounds，会得到bitmap为null

                options.inJustDecodeBounds = true;
                // 但是options中的outWidth  outHeight已经赋值
                // 解析is,得到的bitmap是null
                bitmap= BitmapFactory.decodeStream(is,null,options);


                int outWidth = options.outWidth;
                int outHeight = options.outHeight;

                //图片有默认的长度宽度
                int width = 150;
                int height = 120;

                //构造抽样值，缩放值inSampleSize
                int sampleSize = (outHeight/height > outWidth/width ? outHeight/height :outWidth/width);

                //在还原成false，才能真正的解析流成bitmap
                options.inJustDecodeBounds = false;

                options.inSampleSize = sampleSize;
                options.inPreferredConfig = config;

                bitmap= BitmapFactory.decodeStream(is,null,options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("original 时间:"+(SystemClock.currentThreadTimeMillis()-l));
            System.out.println("original bitmap size:"+(bitmap==null?0:bitmap.getByteCount()));
            try {
                if(is!=null) is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

    }

    //glide访问网络的流程，基本上都是一样的
    private InputStream loadDataWithRedirects(URL url, int redirects, URL lastUrl, Map<String, String> headers)
            throws Exception {
        if (redirects >= 5) {
            throw new Exception("Too many (> " + 5 + ") redirects!");
        } else {
            // Comparing the URLs using .equals performs additional network I/O and is generally broken.
            // See http://michaelscharf.blogspot.com/2006/11/javaneturlequals-and-hashcode-make.html.
            try {
                if (lastUrl != null && url.toURI().equals(lastUrl.toURI())) {
                    throw new Exception("In re-direct loop");
                }
            } catch (Exception e) {
                // Do nothing, this is best effort.
            }
        }
        HttpURLConnection urlConnection = null;//connectionFactory.build(url);
        for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
            urlConnection.addRequestProperty(headerEntry.getKey(), headerEntry.getValue());
        }
        urlConnection.setConnectTimeout(2500);
        urlConnection.setReadTimeout(2500);
        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);

        // Connect explicitly to avoid errors in decoders if connection fails.
        urlConnection.connect();
        if (false) {
            return null;
        }
        final int statusCode = urlConnection.getResponseCode();
        if (statusCode / 100 == 2) {
            return getStreamForSuccessfulRequest(urlConnection);
        } else if (statusCode / 100 == 3) {
            String redirectUrlString = urlConnection.getHeaderField("Location");
            if ((redirectUrlString == null) && redirectUrlString.trim().length()==0) {
                throw new Exception("Received empty or null redirect url");
            }
            URL redirectUrl = new URL(url, redirectUrlString);
            return loadDataWithRedirects(redirectUrl, redirects + 1, url, headers);
        } else {
            if (statusCode == -1) {
                throw new Exception("Unable to retrieve response code from HttpUrlConnection.");
            }
            throw new Exception("Request failed " + statusCode + ": " + urlConnection.getResponseMessage());
        }
    }

    private InputStream getStreamForSuccessfulRequest(HttpURLConnection urlConnection)
            throws Exception {
        InputStream stream = null;//信息流
        if ((urlConnection.getContentEncoding() == null) && urlConnection.getContentEncoding().trim().length()==0) {
            int contentLength = urlConnection.getContentLength();
            stream = null;//ContentLengthInputStream.obtain(urlConnection.getInputStream(), contentLength);
        } else {
            stream = urlConnection.getInputStream();
        }
        return stream;
    }


    public int[] getDimensions(InputStream is,
                               BitmapFactory.Options options) {
        //在解析图片的流  首先使用inJustDecodeBounds，会得到bitmap为null
        // 但是options中的outWidth  outHeight已经赋值
        options.inJustDecodeBounds = true;
        //解析is
        //decodeStream(is, options);
        //在还原成false，才能真正的解析流成bitmap
        options.inJustDecodeBounds = false;
        return new int[] { options.outWidth, options.outHeight };
    }

    public static void getImageFromPhotoFrameworkNet(Context c ,String url,ImageView imageView) {
        Bitmap bitmap = null;//流解析成位图
        InputStream is = null;//信息流
        long l = 0;//时间
        try {

            Glide.with(c).load(url).into(imageView);
            Picasso.with(c).load(url).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("original 时间:"+(SystemClock.currentThreadTimeMillis()-l));
            System.out.println("original bitmap size:"+(bitmap==null?0:bitmap.getByteCount()));
        }
    }
}
