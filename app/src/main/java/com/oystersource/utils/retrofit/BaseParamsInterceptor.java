package com.oystersource.utils.retrofit;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.oystersource.base.BaseApplication;
import com.oystersource.utils.SharedPreferencesHelper;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Zhanglibin on 2017/11/28.
 * 添加消息头和公用参数
 */

public class BaseParamsInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 新的请求,添加参数
        Request addParam = addParam(oldRequest);
        return chain.proceed(addParam);
    }

    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    private Request addParam(Request oldRequest) {
        //token
        String token = "";
        String userId = "";
        BaseApplication.Companion.getInstance();
        SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(BaseApplication.Companion.getInstance(), "LoginBean");
        token = preferencesHelper.getSharedPreference("token", token).toString().trim();
        userId = preferencesHelper.getSharedPreference("userId", userId).toString().trim();
        //app版本号
        String clientVersionNo = "";
        try {
            PackageInfo pi = BaseApplication.instance.getPackageManager().getPackageInfo(BaseApplication.instance.getPackageName(), 0);
            clientVersionNo = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        HttpUrl.Builder builder = oldRequest.url()
                .newBuilder()
                .setEncodedQueryParameter("os", "android")
                .setEncodedQueryParameter("farmerId", userId)
                .setEncodedQueryParameter("clientVersionNo", clientVersionNo);

        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .addHeader("authorization", "Bearer "+token)
                .build();
        return newRequest;
    }
}
