package com.titanic.airbnbclone.exception;

import com.titanic.airbnbclone.utils.ReservationMessage;
import com.titanic.airbnbclone.utils.StatusEnum;
import com.titanic.airbnbclone.web.dto.response.accommodation.DeleteReservationResponseDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationInfoResponseDtoList;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AlreadyReservedException.class)
    public ReservationResponseDto alreadyReserved() {
        return ReservationResponseDto.of (
                StatusEnum.ACCEPTED.getStatusCode(),
                ReservationMessage.ALREADY_RESERVABLE.getMessage());
    }

    @ExceptionHandler(CancelFailException.class)
    public DeleteReservationResponseDto canceledFail() {
        return DeleteReservationResponseDto.builder()
                .status(StatusEnum.ACCEPTED.getStatusCode())
                .message(ReservationMessage.RESERVATION_CANCEL_FAIL.getMessage())
                .build();
    }
}
