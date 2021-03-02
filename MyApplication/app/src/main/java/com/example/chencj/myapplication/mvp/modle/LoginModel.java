package com.example.chencj.myapplication.mvp.modle;

import com.example.commonlib.base.mvp.IModel;

/**
 * Created by CHENCJ on 2021/3/2.
 */

public interface LoginModel extends IModel {
    ///可以返回login的标志来处理，
    // 也可以直接在参数中放入接口回调除登录数据的标志
    //Object login(String username,String password);

    void login(String username,String password,ModelCallback callback);
}
