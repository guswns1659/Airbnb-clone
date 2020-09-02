package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.service.AccommodationService;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.DeleteReservationResponseDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("authorization")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping("{accommodationId}")
    public ReservationResponseDto reserve(@PathVariable Long accommodationId,
                                          @RequestBody ReservationDemandDto reservationDemandDto,
                                          HttpServletRequest request) {
        return accommodationService.reserve(accommodationId, reservationDemandDto, request);
    }

    @DeleteMapping("{accommodationId}/{reservationId}")
    public DeleteReservationResponseDto delete(@PathVariable Long accommodationId,
                                               @PathVariable Long reservationId,
                                               HttpServletRequest request) {
        return accommodationService.delete(accommodationId, reservationId, request);
    }
}
