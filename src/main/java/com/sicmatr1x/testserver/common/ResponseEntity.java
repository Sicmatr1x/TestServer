package com.sicmatr1x.testserver.common;

import java.io.Serializable;

public class ResponseEntity implements Serializable {

    private boolean success;

    private String message;

    private Object data;

    public ResponseEntity() {
        this.setSuccess(true);
    }

    public ResponseEntity(String message, Object data) {
        this();
        this.message = message;
        this.data = data;
    }

    public ResponseEntity(String message) {
        this();
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}