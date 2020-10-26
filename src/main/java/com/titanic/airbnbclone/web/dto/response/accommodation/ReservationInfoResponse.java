package com.titanic.airbnbclone.web.dto.response.accommodation;

import com.titanic.airbnbclone.domain.accommodation.Picture;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationInfoResponse {

    private Long accommodationId;
    private String hotelName;
    private List<Picture> urls;
    private AccountReservationResponse reservation;

    @Builder
    public ReservationInfoResponse(Long accommodationId, String hotelName, List<Picture> urls,
                                   AccountReservationResponse reservation) {
        this.accommodationId = accommodationId;
        this.hotelName = hotelName;
        this.urls = urls;
        this.reservation = reservation;
    }
}
