package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationResponse {

    private String status;
    private String message;

    @Builder
    public ReservationResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ReservationResponse of(String status, String message) {
        return ReservationResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
