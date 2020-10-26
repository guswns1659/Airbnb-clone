package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitAccommodationResponseList {

    private String status;
    private List<InitAccommodationResponse> allData = new ArrayList<>();
    private List<PriceRangeResponse> prices = new ArrayList<>();

    @Builder
    public InitAccommodationResponseList(String status, List<InitAccommodationResponse> allData, List<PriceRangeResponse> prices) {
        this.status = status;
        this.allData = allData;
        this.prices = prices;
    }
}
