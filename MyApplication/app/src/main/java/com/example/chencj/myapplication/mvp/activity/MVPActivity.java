package com.example.chencj.myapplication.mvp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.chencj.myapplication.R;
import com.example.chencj.myapplication.mvp.present.LoginPresentImpl;
import com.example.chencj.myapplication.mvp.view.LoginView;

/**
 * Created by CHENCJ on 2021/3/2.
 */

public class MVPActivity extends Activity implements LoginView {

    private LoginPresentImpl mLoginPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_view);
        mLoginPresent = new LoginPresentImpl();
        mLoginPresent.attachView(this);

    }

    public void mvp(View v){
        //view触发 login
        mLoginPresent.login("zhangsan","123456");
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ObjectUtils.isNotEmpty(mLoginPresent)){
            mLoginPresent.detachView();
        }
    }



    @Override
    public void toast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void loginSuccess(Object object) {

    }

    @Override
    public void loginFail(Object object) {

    }
}
