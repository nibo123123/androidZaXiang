package com.example.chencj.myapplication.mvp.present;

import com.blankj.utilcode.util.StringUtils;
import com.example.chencj.myapplication.mvp.modle.LoginModel;
import com.example.chencj.myapplication.mvp.modle.LoginModelImpl;
import com.example.chencj.myapplication.mvp.modle.ModelCallback;
import com.example.chencj.myapplication.mvp.view.LoginView;
import com.example.commonlib.base.mvp.BasePresenter;

/**
 * Created by CHENCJ on 2021/3/2.
 */

public class LoginPresentImpl extends BasePresenter<LoginModel,LoginView> implements LoginPresent {

    /**
     * 实现登录
     * @param username
     * @param password
     */
    @Override
    public void login(String username, String password) {

        if(StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(password)){
            getView().toast("密码或用户名 null");
            return;
        }

        LoginModel model = getModel();
        if(model == null){
            getView().toast("model is null");
            return;
        }
        //调model处理的结构
        model.login(username, password,
                /*//根据结果通过view处理页面*/
                new ModelCallback() {
                    @Override
                    public void success(Object success) {
                        getView().toast((String) success);
                    }

                    @Override
                    public void fail(Object fail) {
                        getView().toast((String) fail);
                    }
                });

    }

    @Override
    public void onStart() {

    }

    @Override
    public LoginModel createModel() {
        return new LoginModelImpl();
    }
}
