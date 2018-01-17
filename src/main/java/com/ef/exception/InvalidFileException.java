package com.ef.exception;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(Exception e) {
        super(e);
    }

    public InvalidFileException(String errorMessage) {
        super(errorMessage);
    }
}
