package com.example.chencj.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.chencj.myapplication.activity.RecycleViewActivity;
import com.example.chencj.myapplication.flow.FlowActivity;
import com.example.chencj.myapplication.hook.HookActivity;
import com.example.chencj.myapplication.surfacefling.SurfaceDemoActivity;
import com.example.chencj.myapplication.util.ViewAnimatorUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_LOCATION = 1;
    private TextView mTvTitle;
    private ImageView iv1;
    private LocationManager lm;
    private String bestProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(View.inflate(this, R.layout.activity_main, null));
        //initGlide();
        //initPicasso();
        iv1 = (ImageView) findViewById(R.id.iv1);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 判断GPS是否正常启动
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ToastUtils.showShort("enable gsp");
        }
    }

    public void click_btn(View v) {
        ViewAnimatorUtils.mixViewAnimation(this, iv1, R.anim.mix_animator);

    }

    public void button_recycleview(View v) {
        startActivity(new Intent(this, RecycleViewActivity.class));
    }

    /**
     * 实现方式（补间动画均可以通过代码和Xml两种方式实现）。
     */


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void button_location(View v) {
        // 为获取地理位置信息时设置查询条件
        bestProvider = lm.getBestProvider(getCriteria(), true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            }
            return;
        }
        if(bestProvider != null) {
            Location location = lm.getLastKnownLocation(bestProvider);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        }
    }

    public void button_hook(View v) {
        startActivity(new Intent(this, HookActivity.class));
    }

    public void button_flow(View v) {
        startActivity(new Intent(this, FlowActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                          int[] grantResults){
       if(requestCode == REQUEST_LOCATION){
           Log.d("MainActivity chencj ", "onRequestPermissionsResult: grantResults="+ Arrays.toString(grantResults));
           Log.d("MainActivity chencj ", "onRequestPermissionsResult: permissions="+ Arrays.toString(permissions));
           if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

           }
       }
    }

    private static String getGpsLoaalTime(long gpsTime){
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(gpsTime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String datestring = df.format(calendar.getTime());

        return datestring;
    }

    // 位置监听
    private LocationListener locationListener = new LocationListener() {

        /**
         * 位置信息变化时触发
         */
        public void onLocationChanged(Location location) {
            Log.i(TAG, "时间：" + location.getTime()+",time="+getGpsLoaalTime(location.getTime()));
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("MainActivity chencj ", "onStatusChanged: provider="+provider+",status="+status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
            Log.d("MainActivity chencj ", "onProviderEnabled: provider="+provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
            Log.d("MainActivity chencj ", "onProviderDisabled: provider="+provider);
        }
    };


    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    public void button_surfacefling(View v) {
        startActivity(new Intent(this, SurfaceDemoActivity.class));
    }
    public void encodesms(View v) {
        /*SmsMessage.SubmitPdu submitPdu = SmsMessage.getSubmitPdu("10086", "1008611", "aä", false);
        byte[] encodedMessage = submitPdu.encodedMessage;
        for (int i = 0; i < encodedMessage.length; i++) {
            Log.d("MainActivity chencj ", "encodesms: "+Integer.toHexString(encodedMessage[i])+" ");
        }*/
        mHandle.sendEmptyMessageDelayed(100,1000);
    }

    public void chart(View v){
        //startActivity(new Intent(this, ChartDemoActivity.class));
        //startActivity(new Intent(this, ChartsDemo1Activity.class));
        mHandle.removeMessages(100);
        Log.d("MainActivity chencj ", "chart: "+mHandle.hasMessages(100));
    }

    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("MainActivity chencj ", "handleMessage: "+msg.what);
        }
    };
}
