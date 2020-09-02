package com.titanic.airbnbclone.utils;

public enum StatusEnum {
    SUCCESS("200"), ACCEPTED("202");

    private String statusCode;

    StatusEnum(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
