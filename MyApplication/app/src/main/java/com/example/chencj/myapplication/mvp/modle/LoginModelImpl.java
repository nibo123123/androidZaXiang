package com.example.chencj.myapplication.mvp.modle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by CHENCJ on 2021/3/2.
 */

public class LoginModelImpl implements LoginModel {
    @Override
    public void login(String username, String password, final ModelCallback callback) {
        //从present调到这里
        // 处理结果，通过callback，回调到present

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.url("http://www.weather.com.cn/data/sk/101010100.html").get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.fail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body==null){
                    callback.fail("body null");
                }
                callback.success(body.string());
            }
        });

    }
}
