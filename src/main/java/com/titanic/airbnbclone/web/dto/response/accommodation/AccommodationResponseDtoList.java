package com.titanic.airbnbclone.web.dto.response.accommodation;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class AccommodationResponseDtoList {

    private String status;
    private List<AccommodationResponseDto> allData;
    private List<PriceRangeResponseDto> prices;

    @Builder
    public AccommodationResponseDtoList(String status, List<AccommodationResponseDto> allData, List<PriceRangeResponseDto> prices) {
        this.status = status;
        this.allData = allData;
        this.prices = prices;
    }
}
