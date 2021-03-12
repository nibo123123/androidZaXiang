package com.example.chencj.myapplication.multiprocess;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chencj.myapplication.R;


/**
 * Created by CHENCJ on 2021/2/24.
 */

public class SecondActivity extends Activity {
    private Button mBtnGetSp;
    private Button mBtnSaveSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiprocess_second);

        mBtnGetSp = (Button)findViewById(R.id.btn_get_sp_2);


        mBtnGetSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getValue(SecondActivity.this, "name", "cr");
                Toast.makeText(SecondActivity.this,name,Toast.LENGTH_SHORT).show();
            }
        });

        mBtnSaveSp = (Button)findViewById(R.id.btn_save_sp_2);

        mBtnSaveSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this,"87654321",Toast.LENGTH_SHORT).show();
                setValue(SecondActivity.this,"name1","87654321");
            }
        });
    }


    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_KEY = "key";
    private static final String EXTRA_VALUE = "value";
    private static final int TYPE_STRING = 1;
    //访问对方ContentProvider的uri
    public static final Uri CONTENT_PROVIDER_URI = Uri.parse("content://com.example.chencj.myapplication.multiprocess");
    private static final int LENGTH_CONTENT_URI	= CONTENT_PROVIDER_URI.toString().length() + 1;

    /**
     *
     * @param key
     * @param value
     * @param context
     */
    public static void setValue( Context context,String key , String value ){
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(EXTRA_TYPE, TYPE_STRING);
        contentvalues.put(EXTRA_KEY, key);
        contentvalues.put(EXTRA_VALUE, value);

        try {
            //使用contentprovider来进行跨进程
            //主要是通过getContentResolver通过MY_CONTENT_PROVIDER_URI
            //在getContentResolver通过aidl跨进程处理
            // 对应的uri的authorities找到实现的contentProvider

            //插入使用update，不是真正的去执行slq
            //只是使用ContentProvider的跨进程能力
            // 去找到对方的SP，把结果塞进去

            context.getContentResolver().update(CONTENT_PROVIDER_URI, contentvalues, null, null);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public static String getValue( Context context,String key ,String defValue){
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(EXTRA_TYPE, TYPE_STRING);
        contentvalues.put(EXTRA_KEY, key);
        contentvalues.put(EXTRA_VALUE, defValue);

        Uri result;

        try {
            //使用contentprovider来进行跨进程
            //主要是通过getContentResolver通过MY_CONTENT_PROVIDER_URI
            //在getContentResolver通过aidl跨进程处理
            // 对应的uri的authorities找到实现的contentProvider

            //只是使用ContentProvider的跨进程能力
            //通过insert是返回uri可以作为SP结果封装里面
            result = context.getContentResolver().insert(CONTENT_PROVIDER_URI, contentvalues);
        } catch (Exception e) {
            return defValue;
        }

        if (result == null) {
            return defValue;
        }

        return result.toString().substring(LENGTH_CONTENT_URI);
    }
}
