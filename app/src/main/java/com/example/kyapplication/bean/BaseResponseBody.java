package com.example.kyapplication.bean;

public class BaseResponseBody<T> {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    private int errorCode;

    private String errorMsg;

    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return SUCCESS == errorCode;
    }

    @Override
    public String toString() {
        return "BaseResponseBody{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
