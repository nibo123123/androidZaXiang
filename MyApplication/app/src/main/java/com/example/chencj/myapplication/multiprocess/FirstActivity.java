package com.example.chencj.myapplication.multiprocess;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chencj.myapplication.R;

/**
 * Created by CHENCJ on 2021/2/24.
 */

public class FirstActivity extends Activity {
    private Button mBtnSaveSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiprocess_first);

        mBtnSaveSp = (Button)findViewById(R.id.btn_save_sp);
        Log.d("FirstActivity chencj ", "onCreate: processName = "
                + MultiProcessSharedPreferencesManager.getInstance(FirstActivity.this).getProcessName());

        mBtnSaveSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstActivity.this,"123456",Toast.LENGTH_SHORT).show();
                MultiProcessSharedPreferencesManager.getInstance(FirstActivity.this).setString("name", "123456");
            }
        });
    }
}
