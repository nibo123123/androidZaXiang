package com.example.chencj.myapplication.mvp.present;

import com.example.commonlib.base.mvp.IPresenter;

/**
 * Created by CHENCJ on 2021/3/2.
 */

public interface LoginPresent extends IPresenter{

    //
    void login(String username,String password);
}
