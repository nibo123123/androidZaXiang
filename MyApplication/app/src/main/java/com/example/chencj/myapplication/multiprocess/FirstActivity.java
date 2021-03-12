package com.example.chencj.myapplication.multiprocess;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chencj.myapplication.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by CHENCJ on 2021/2/24.
 */

public class FirstActivity extends Activity {
    private Button mBtnSaveSp;
    private Button mBtnGetSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiprocess_first);

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i);
            int pid = runningAppProcessInfo.pid;
            String processName = runningAppProcessInfo.processName;
            int uid = runningAppProcessInfo.uid;
            String[] pkgList = runningAppProcessInfo.pkgList;
            Log.d("FirstActivity chencj ", "onCreate: pid="+pid+",processname="+processName+",uid="+uid+",pkglist="+ Arrays.toString(pkgList));
            /**
             pid=2927,processname=com.example.chencj.myapplication,uid=10071,pkglist=[com.example.chencj.myapplication]
             pid=2968,processname=com.process.self2,uid=10071,pkglist=[com.example.chencj.myapplication]
             */
        }

        mBtnSaveSp = (Button)findViewById(R.id.btn_save_sp_1);

        mBtnSaveSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstActivity.this,"12345678",Toast.LENGTH_SHORT).show();
                MultiProcessSharedPreferencesManager.getInstance(FirstActivity.this).setString("name", "12345678");
            }
        });

        mBtnGetSp = (Button)findViewById(R.id.btn_get_sp_1);

        mBtnGetSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = MultiProcessSharedPreferencesManager.getInstance(FirstActivity.this).getString("name1", "or");
                Toast.makeText(FirstActivity.this,name1,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
