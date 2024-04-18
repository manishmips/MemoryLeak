package com.adp.leakcanary.model.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.intellij.lang.annotations.Identifier;

import java.util.List;

public class BaseResponse<T> {

    @SerializedName("statusCode")
    @Expose
    private int statusCode;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private T data;




    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
