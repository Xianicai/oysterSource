package com.oystersource.utils.retrofit.factory;


import com.oystersource.utils.retrofit.UnicodeUtil;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Zhanglibin on 2017/11/29.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;


    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = UnicodeUtil.decodeUnicode(value.string()).trim();
//        BaseBean baseBean = gson.fromJson(response, BaseBean.class);
//        if (baseBean.isSuccess()) {
//        } else {
//            ToastUtil.INSTANCE.showMessage(baseBean.getMessage());
//        }
        return gson.fromJson(response, type);


        //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
//        HttpResult httpResult = gson.fromJson(response, HttpResult.class);
//        if (httpResult.getCode() == 100) {
//            //200的时候就直接解析，不可能出现解析异常。因为我们实体基类中传入的泛型，就是数据成功时候的格式
//            return gson.fromJson(response, type);
//        } else {
//            HttpResult errorResponse = gson.fromJson(response, HttpResult.class);
//            //抛一个自定义ResultException 传入失败时候的状态码，和信息
//            throw new ResultException(errorResponse.getCode(), errorResponse.getMessage());
//        }
    }

}