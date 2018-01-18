package com.ef.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(Exception e) {
        super(e);
    }

    public InvalidParameterException(String invalidParam) {
        super(invalidParam + " parameter is required!");
    }
}
