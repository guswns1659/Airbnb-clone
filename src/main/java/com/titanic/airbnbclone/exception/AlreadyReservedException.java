package com.titanic.airbnbclone.exception;

public class AlreadyReservedException extends RuntimeException {
    public AlreadyReservedException() {
    }

    public AlreadyReservedException(String message) {
        super(message);
    }
}
