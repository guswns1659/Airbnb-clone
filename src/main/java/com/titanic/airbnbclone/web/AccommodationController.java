package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.service.AccommodationService;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationRequest;
import com.titanic.airbnbclone.web.dto.response.accommodation.DeleteReservationResponse;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationInfoResponseList;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("authorization")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping("{accommodationId}")
    public ReservationResponse reserve(@PathVariable Long accommodationId,
                                       @RequestBody ReservationRequest reservationRequest,
                                       HttpServletRequest request) {
        return accommodationService.reserve(accommodationId, reservationRequest, request);
    }

    @DeleteMapping("{accommodationId}/{reservationId}")
    public DeleteReservationResponse delete(@PathVariable Long accommodationId,
                                            @PathVariable Long reservationId,
                                            HttpServletRequest request) {
        return accommodationService.delete(accommodationId, reservationId, request);
    }

    @GetMapping("reservationInfo")
    public ReservationInfoResponseList getReservationInfo(HttpServletRequest request) {
        return accommodationService.getReservationInfo(request);
    }
}
