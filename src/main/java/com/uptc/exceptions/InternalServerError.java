package com.uptc.exceptions;

public class InternalServerError extends RuntimeException {
    
    private static final String DESCRIPTION = "Internal Server Error";

    public InternalServerError(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
