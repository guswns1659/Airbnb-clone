package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.service.AccommodationService;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDtoList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("accommodation")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("init")
    public AccommodationResponseDtoList getInitAccommodations() {
        return accommodationService.getInitInfo();
    }
}
