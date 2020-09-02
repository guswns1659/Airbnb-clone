package com.titanic.airbnbclone.exception;

public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException() {
    }

    public NoSuchEntityException(Long entityId) {
        super("해당 엔티티가 없습니다. : " + entityId);
    }
}
