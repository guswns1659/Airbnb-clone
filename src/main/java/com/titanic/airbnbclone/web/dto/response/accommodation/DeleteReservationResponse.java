package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteReservationResponse {

    private String status;
    private String message;

    @Builder
    public DeleteReservationResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
