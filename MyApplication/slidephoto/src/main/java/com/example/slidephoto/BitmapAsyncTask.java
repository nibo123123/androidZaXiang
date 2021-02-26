package com.example.slidephoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.slidephoto.utils.IOUtils;
import com.example.slidephoto.utils.StringUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CHENCJ on 2021/2/25.
 */

public class BitmapAsyncTask extends AsyncTask<String,Integer,Bitmap> {

    /**
     * 用来更新bitmap的回调
     */
    interface UpdateBitmapInterface{
        void postUpdateBitmap(Bitmap bitmap);
    }

    private UpdateBitmapInterface updateBitmapInterface;

    public void setUpdateBitmapInterface(UpdateBitmapInterface updateBitmapInterface) {
        this.updateBitmapInterface = updateBitmapInterface;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == 200){
                is = connection.getInputStream();
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
                int width = 400;
                int height = 300;

                //构造抽样值，缩放值inSampleSize
                int sampleSize = (outHeight/height > outWidth/width ? outHeight/height :outWidth/width);

                //在还原成false，才能真正的解析流成bitmap
                options.inJustDecodeBounds = false;

                options.inSampleSize = sampleSize;
                options.inPreferredConfig = config;

                bitmap= BitmapFactory.decodeStream(is,null,options);
            }
        }catch (Exception e){
            Log.e("BitmapAsyncTask chencj ", "doInBackground: ", e);
        }finally {
            IOUtils.closeIO(is);
            IOUtils.closeURLConnection(connection);
        }

        return bitmap;
    }

    //主线程更新ui
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(StringUtils.isNotNull(updateBitmapInterface)){
            updateBitmapInterface.postUpdateBitmap(bitmap);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }
}
