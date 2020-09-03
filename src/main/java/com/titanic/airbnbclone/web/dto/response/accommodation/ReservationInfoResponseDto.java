package com.titanic.airbnbclone.web.dto.response.accommodation;

import com.titanic.airbnbclone.domain.accommodation.Picture;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationInfoResponseDto {

    private Long accommodationId;
    private String hotelName;
    private List<Picture> urls;
    private AccountReservationResponseDto reservation;

    @Builder
    public ReservationInfoResponseDto(Long accommodationId, String hotelName, List<Picture> urls,
                                      AccountReservationResponseDto reservation) {
        this.accommodationId = accommodationId;
        this.hotelName = hotelName;
        this.urls = urls;
        this.reservation = reservation;
    }
}
