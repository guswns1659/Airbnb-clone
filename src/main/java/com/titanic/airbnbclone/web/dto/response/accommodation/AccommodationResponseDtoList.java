package com.titanic.airbnbclone.web.dto.response.accommodation;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationResponseDtoList {

    private String status;
    private List<AccommodationResponseDto> allData = new ArrayList<>();
    private List<PriceRangeResponseDto> prices = new ArrayList<>();

    @Builder
    public AccommodationResponseDtoList(String status, List<AccommodationResponseDto> allData, List<PriceRangeResponseDto> prices) {
        this.status = status;
        this.allData = allData;
        this.prices = prices;
    }
}
