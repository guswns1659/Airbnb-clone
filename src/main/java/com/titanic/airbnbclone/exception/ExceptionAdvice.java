package com.titanic.airbnbclone.exception;

import com.titanic.airbnbclone.utils.ReservationMessage;
import com.titanic.airbnbclone.utils.StatusEnum;
import com.titanic.airbnbclone.web.dto.response.accommodation.DeleteReservationResponse;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AlreadyReservedException.class)
    public ReservationResponse alreadyReserved() {
        return ReservationResponse.of (
                StatusEnum.ACCEPTED.getStatusCode(),
                ReservationMessage.ALREADY_RESERVABLE.getMessage());
    }

    @ExceptionHandler(CancelFailException.class)
    public DeleteReservationResponse canceledFail() {
        return DeleteReservationResponse.builder()
                .status(StatusEnum.ACCEPTED.getStatusCode())
                .message(ReservationMessage.RESERVATION_CANCEL_FAIL.getMessage())
                .build();
    }
}
