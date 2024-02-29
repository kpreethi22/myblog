package com.my.blog.my.blog.exception;

import org.springframework.http.HttpStatus;

public class MyBlogAPIException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public MyBlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MyBlogAPIException(String message, HttpStatus status, String message1) {
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
