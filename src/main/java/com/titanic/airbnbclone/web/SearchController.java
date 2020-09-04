package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.service.AccommodationService;
import com.titanic.airbnbclone.web.dto.request.accommodation.FilterRequestDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDtoList;
import com.titanic.airbnbclone.web.dto.response.accommodation.InitAccommodationResponseDtoList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final AccommodationService accommodationService;

    @GetMapping("init")
    public InitAccommodationResponseDtoList getInitAccommodations() {
        return accommodationService.getInitInfo();
    }

    @PostMapping("filter")
    public AccommodationResponseDtoList getFiltered(@RequestBody FilterRequestDto filterRequestDto) {
        return accommodationService.getFiltered(filterRequestDto);
    }
}
