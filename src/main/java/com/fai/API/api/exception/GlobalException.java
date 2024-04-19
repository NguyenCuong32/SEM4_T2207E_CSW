package com.fai.API.api.exception;

public class GlobalException extends RuntimeException{

    private Integer statusCode;

    public GlobalException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}
