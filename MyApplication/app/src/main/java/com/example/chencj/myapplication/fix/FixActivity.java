package com.example.chencj.myapplication.fix;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chencj.myapplication.R;

/**
 * Created by CHENCJ on 2021/3/5.
 */

public class FixActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fix_activity_view);
    }

    public void fixed(View v){
        Toast.makeText(this,"failed"+(1/0),Toast.LENGTH_SHORT).show();
    }
}
