package com.titanic.airbnbclone.exception;

public class GetReservedInfoFailException extends RuntimeException {

    public GetReservedInfoFailException() {
    }

    public GetReservedInfoFailException(String message) {
        super(message);
    }
}
