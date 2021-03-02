package com.example.chencj.myapplication.mvp.view;

import com.example.commonlib.base.mvp.IView;

/**
 * Created by CHENCJ on 2021/3/2.
 */

public interface LoginView extends IView {
    //增加V的相关接口
    //设计到view的一些需要被activity继承实现
    //view又被present持有 ，present又被activity持有

    /**
     * toast出对应的信息
     * @param msg
     */
    void toast(String msg);

    /**
     * 成功登录跳转页面
     * @param object
     */
    void loginSuccess(Object object);

    /**
     * 失败登录跳转页面
     * @param object
     */
    void loginFail(Object object);


}
