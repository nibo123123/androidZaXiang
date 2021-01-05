package com.example.chencj.myapplication.hook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chencj.myapplication.R;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by CHENCJ on 2020/12/24.
 */

public class HookActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_view);
        Button button = (Button)findViewById(R.id.hookBtn);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HookActivity.this, "click hook", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HookActivity.this,NoRegisterActivity.class));
            }
        };
        Log.d("HookActivity chencj ", "onCreate: onClickListenerProxy="+onClickListener);
        button.setOnClickListener(onClickListener);
        //HookHelper.hookOnClickListener(this,button);
        try {
            HookHelper.hookActivityThreadMh(this);
            HookHelper.hookAMSStartActivity(this);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
