package com.titanic.airbnbclone.exception;

public class ReservedFailException extends RuntimeException {

    public ReservedFailException() {
    }

    public ReservedFailException(String message) {
        super(message);
    }
}
