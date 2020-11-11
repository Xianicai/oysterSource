package com.oystersource.utils.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.elvishew.xlog.XLog;
import com.oystersource.base.BaseApplication;
import com.oystersource.utils.ToastUtil;

import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Zhanglibin on 2017/4/25.
 */

public class LoggerInterceptor implements Interceptor {

    public static final String TAG = "NetWorkLogger";

    @Override
    public Response intercept(Chain chain) throws IOException {
        //判断是否有网
        isNetworkAvailable();
        Request request = chain.request();
        //开始请求
        printRequestMessage(request);
        Response response = chain.proceed(request);
        //请求之后的信息打印
        printResponseMessage(response);
        return response;
    }

    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private void printRequestMessage(Request request) {
        if (request == null) {
            return;
        }
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            XLog.d("请求开始\nMethod: " + request.method() + "\nUrl   : " + request.url().url().toString());
            return;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = requestBody.contentType().charset();
            charset = charset == null ? Charset.forName("utf-8") : charset;
            XLog.d("请求开始\nMethod: " + request.method() + "\nUrl   : " + request.url().url().toString() + "\nParams: " + bufferedSink.readString(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private void printResponseMessage(Response response) {
        if (response == null || !response.isSuccessful()) {
            return;
        }
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            // Buffer the entire body.
            source.request(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (contentLength != 0 && charset != null) {
            String result = buffer.clone().readString(charset);
            if (response.request().body() == null) {
                XLog.d("请求结束\nMethod: " + response.request().method() + "\nUrl   : " + response.request().url().toString() + "\n" + result);
            } else {
                try {
                    Buffer bufferedSink = new Buffer();
                    response.request().body().writeTo(bufferedSink);
                    XLog.d("请求结束\nMethod: " + response.request().method() + "\nUrl   : " + response.request().url().toString() + "\nParams: " + bufferedSink.readString(charset) + "\n" + result);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            XLog.d(result);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.Companion.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            ToastUtil.INSTANCE.showMessage("您的网络不太顺畅哦~");
            return false;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        ToastUtil.INSTANCE.showMessage("您的网络不太顺畅哦~");
        return false;

    }
}
