package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationResponseDto {

    private String status;
    private String message;

    @Builder
    public ReservationResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ReservationResponseDto of(String status, String message) {
        return ReservationResponseDto.builder()
                .status(status)
                .message(message)
                .build();
    }
}
