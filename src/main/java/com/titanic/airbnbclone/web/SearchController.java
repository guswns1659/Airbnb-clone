package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.service.AccommodationService;
import com.titanic.airbnbclone.web.dto.request.accommodation.FilterRequest;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseList;
import com.titanic.airbnbclone.web.dto.response.accommodation.InitAccommodationResponseList;
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
    public InitAccommodationResponseList getInitAccommodations() {
        return accommodationService.getInitInfo();
    }

    @PostMapping("filter")
    public AccommodationResponseList getFiltered(@RequestBody FilterRequest filterRequest) {
        return accommodationService.getFiltered(filterRequest);
    }
}
