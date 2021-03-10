package com.example.chencj.myapplication.fix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chencj.myapplication.R;
import com.example.chencj.myapplication.activity.EmptyActivity;
import com.example.chencj.myapplication.reflect.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by CHENCJ on 2021/3/5.
 */

public class FixActivity extends Activity {
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fix_activity_view);
        editText = (EditText) findViewById(R.id.fix_edit);
        textView = (TextView) findViewById(R.id.fix_text);

        CharSequence content = "123456789这是一首诗，打油诗，一去二三里，湾村四五家，亭台六七在，八九十之花。very good！";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
        spannableStringBuilder.setSpan(foregroundColorSpan, 4, 7, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        editText.setText(spannableStringBuilder);

        String s = Html.toHtml(spannableStringBuilder);
        System.out.println("==="+s);
        String html="<p><span style='color: #ff0000;'>我的</span><b>测</b><s>试</s></p>"/*<img src=\"ic_launcher-web.png\"><img src=\"\">*/;
        CharSequence charSequence= Html.fromHtml(s);
        textView.setText(charSequence);

        findViewById(R.id.demoview_id).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("FixActivity chencj ", "onTouch: event="+event);
                return true;
            }
        });
    }

    public void fixed(View v){
        //Toast.makeText(this,"failed"+(1/0),Toast.LENGTH_SHORT).show();
        Method startActivity = ReflectionUtil.getMethod("android.app.Activity", "startActivity", new Class[]{Intent.class});
        Intent intent = new Intent(this,EmptyActivity.class);
        try {
            startActivity.invoke(this,new Object[]{intent});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
