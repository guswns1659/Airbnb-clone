package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.repository.AccommodationRepository;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDtoList;
import com.titanic.airbnbclone.web.dto.response.accommodation.PriceRangeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public AccommodationResponseDtoList getInitAccommodations() {
        return null;
    }

    public List<PriceRangeResponseDto> classifyAccommodationPrice() {
        return accommodationRepository.classifyAccommodationPrice();
    }
}
