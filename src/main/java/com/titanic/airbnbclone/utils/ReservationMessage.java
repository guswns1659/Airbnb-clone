package com.titanic.airbnbclone.utils;

public enum ReservationMessage {
    ALREADY_RESERVABLE("해당 날짜엔 이미 예약이 있습니다."), RESERVATION_SUCCESS("예약에 성공했습니다."),
    RESERVATION_FAIL("예약에 실패했습니다."),
    RESERVATION_CANCEL_FAIL("예약 취소에 실패했습니다."), RESERVATION_CANCEL_SUCCESS("예약 취소에 성공했습니다.");

    private String message;

    ReservationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
