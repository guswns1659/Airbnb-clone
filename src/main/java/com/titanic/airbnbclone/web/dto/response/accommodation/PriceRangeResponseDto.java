package com.titanic.airbnbclone.web.dto.response.accommodation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NegativeOrZero;

@Getter
@ToString
@NoArgsConstructor
public class PriceRangeResponseDto {

    private int total;
    private int price;

    public void plusTotal() {
        this.total++;
    }
}
