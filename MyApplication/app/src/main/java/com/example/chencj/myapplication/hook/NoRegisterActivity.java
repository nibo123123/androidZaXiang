package com.example.chencj.myapplication.hook;

import android.app.Activity;
import android.os.Bundle;

import com.example.chencj.myapplication.R;

/**
 * Created by CHENCJ on 2020/12/24.
 */

public class NoRegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_no_view);
    }
}
