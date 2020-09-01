package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.repository.AccommodationRepository;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDtoList;
import com.titanic.airbnbclone.web.dto.response.accommodation.PriceRangeResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public AccommodationResponseDtoList getInitInfo() {
        return AccommodationResponseDtoList.builder()
                .allData(getInitAccommodation())
                .prices(classifyAccommodationPrice())
                .status(String.valueOf(HttpStatus.SC_OK))
                .build();
    }

    @Transactional
    public List<PriceRangeResponseDto> classifyAccommodationPrice() {
        return accommodationRepository.classifyAccommodationPrice();
    }

    public List<AccommodationResponseDto> getInitAccommodation() {
        return accommodationRepository.getInitAccommodation();
    }
}
