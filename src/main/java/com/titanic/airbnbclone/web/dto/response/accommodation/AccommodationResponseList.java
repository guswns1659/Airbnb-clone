package com.titanic.airbnbclone.web.dto.response.accommodation;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationResponseList {

    private String status;
    private List<AccommodationResponse> allData = new ArrayList<>();
    private List<PriceRangeResponse> prices = new ArrayList<>();

    @Builder
    public AccommodationResponseList(String status, List<AccommodationResponse> allData, List<PriceRangeResponse> prices) {
        this.status = status;
        this.allData = allData;
        this.prices = prices;
    }
}
