package com.project.response;

import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class AppResponse {
    private String message;
    private int code;
    private boolean success;
    private Map<String, Object> data;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public Object getData() {
        return data;
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
