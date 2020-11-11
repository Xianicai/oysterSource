package com.oystersource.utils.retrofit;

/**
 * Created by Zhanglibin on 2017/4/24.
 */

public class HttpResult<T> {

    int code;
    String message;
    T content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
