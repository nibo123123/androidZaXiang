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

public class SecondActivity extends Activity {
    private Button mBtnGetSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiprocess_second);

        mBtnGetSp = (Button)findViewById(R.id.btn_get_sp);
        Log.d("SecondActivity chencj ", "onCreate: processName = "
                + MultiProcessSharedPreferencesManager.getInstance(SecondActivity.this).getProcessName());

        mBtnGetSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = MultiProcessSharedPreferencesManager.getInstance(SecondActivity.this).getString("name", "cr");
                Log.d("SecondActivity chencj ", "onClick: name="+name);
                Toast.makeText(SecondActivity.this,name,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
