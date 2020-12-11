package com.micray.springdemo.model;

public class ResponseMessage {
    private String result;
    private Object data;

    public ResponseMessage(String result, Object data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
