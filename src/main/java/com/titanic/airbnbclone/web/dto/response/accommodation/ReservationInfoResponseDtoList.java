package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationInfoResponseDtoList {

    private String status;
    private List<ReservationInfoResponseDto> allData;

    @Builder
    public ReservationInfoResponseDtoList(String status, List<ReservationInfoResponseDto> allData) {
        this.status = status;
        this.allData = allData;
    }
}
