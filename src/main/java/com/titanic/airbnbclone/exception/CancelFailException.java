package com.titanic.airbnbclone.exception;

public class CancelFailException extends RuntimeException {

    public CancelFailException() {
    }

    public CancelFailException(String message) {
        super(message);
    }
}
