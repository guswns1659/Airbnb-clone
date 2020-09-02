package com.titanic.airbnbclone.exception;

public class AlreadyReservationException extends RuntimeException {
    public AlreadyReservationException() {
    }

    public AlreadyReservationException(String message) {
        super(message);
    }
}
