package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationInfoResponseList {

    private String status;
    private List<ReservationInfoResponse> allData;

    @Builder
    public ReservationInfoResponseList(String status, List<ReservationInfoResponse> allData) {
        this.status = status;
        this.allData = allData;
    }
}
