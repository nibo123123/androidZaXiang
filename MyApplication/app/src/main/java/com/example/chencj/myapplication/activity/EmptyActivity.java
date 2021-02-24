package com.example.chencj.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by CHENCJ on 2021/2/21.
 */

public class EmptyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"empty activity",Toast.LENGTH_SHORT).show();
        finish();
    }

}
