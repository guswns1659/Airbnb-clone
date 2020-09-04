package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitAccommodationResponseDtoList {

    private String status;
    private List<InitAccommodationResponseDto> allData = new ArrayList<>();
    private List<PriceRangeResponseDto> prices = new ArrayList<>();

    @Builder
    public InitAccommodationResponseDtoList(String status, List<InitAccommodationResponseDto> allData, List<PriceRangeResponseDto> prices) {
        this.status = status;
        this.allData = allData;
        this.prices = prices;
    }
}
