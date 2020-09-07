package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.web.dto.response.accommodation.InitAccommodationResponseDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.PriceRangeResponseDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccommodationServiceTest {

    @Autowired
    private AccommodationService accommodationService;

    @ParameterizedTest
    @CsvSource({"10000,92"})
    void 가격분류메서드를_테스트한다(int price, int total) {
        // when
        List<PriceRangeResponseDto> priceRangeResponseDtos
                = accommodationService.classifyByPrice();

        // then
        assertThat(priceRangeResponseDtos.get(0).getPrice()).isEqualTo(price);
        assertThat(priceRangeResponseDtos.get(0).getTotal()).isEqualTo(total);
    }

    @ParameterizedTest
    @CsvSource("30")
    void 숙박30개데이터를_요청한다(int size) {
        // when
        List<InitAccommodationResponseDto> accommodationResponseDtos =
                accommodationService.getInitAccommodation();
        // then
        assertThat(accommodationResponseDtos.size()).isEqualTo(size);
    }
}
