package com.stone.springbootrestblog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class BlogApiException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String message;

    public BlogApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public BlogApiException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

}
