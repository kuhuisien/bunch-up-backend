package com.springboot.bunch.exception;

import org.springframework.http.HttpStatus;

public class BunchAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BunchAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BunchAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
