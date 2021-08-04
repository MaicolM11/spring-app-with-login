package com.uptc.exceptions;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorMessage {

    private Date timestamp;
    private final String error;
    private String message;
    private final Integer statusCode;

    public ErrorMessage(Exception exception, Integer code) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.statusCode = code;
        this.timestamp = new Date();
    }

}