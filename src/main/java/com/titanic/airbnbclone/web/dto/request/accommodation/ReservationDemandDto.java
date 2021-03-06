package com.titanic.airbnbclone.web.dto.request.accommodation;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationDemandDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer people;
    private Integer totalPrice;

    @Builder
    public ReservationDemandDto(LocalDate startDate, LocalDate endDate, Integer people, Integer totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.people = people;
        this.totalPrice = totalPrice;
    }
}
