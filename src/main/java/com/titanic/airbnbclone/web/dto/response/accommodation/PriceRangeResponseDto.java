package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PriceRangeResponseDto extends PriceRange {

    private int price;
    private int total;

    public PriceRangeResponseDto(int price, int total) {
        this.price = price;
        this.total = total;
    }
}
