package com.adp.leakcanary.model.base;

public class Result<T> {

    private T data;
    private FailureResponse failureResponse;
    private Status status;
    private String msg;
    private int code;
    private int statusCode;

    public Result(T data, int statusCode, String msg, FailureResponse failureResponse, Status status) {
        this.data = data;
        this.failureResponse = failureResponse;
        this.status = status;
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public Result(T data, FailureResponse failureResponse, Status status) {
        this.data = data;
        this.failureResponse = failureResponse;
        this.status = status;
    }

    public Result(FailureResponse failureResponse) {
        this.data = null;
        this.failureResponse = failureResponse;
        this.status = Status.FAILURE;
    }

    public Result(T data) {
        this.data = data;
        this.failureResponse = null;
        this.status = Status.SUCCESS;
    }

    public Result(T data, Status status) {
        this.data = data;
        this.failureResponse = null;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

    public T getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getCode() {
        return code;
    }

    public FailureResponse getFailureResponse() {
        return failureResponse;
    }

    public boolean isFailed() {
        return status == Status.FAILURE;
    }

    public enum Status {
        SUCCESS,
        FAILURE,
        INPROGRESS;
    }
}
