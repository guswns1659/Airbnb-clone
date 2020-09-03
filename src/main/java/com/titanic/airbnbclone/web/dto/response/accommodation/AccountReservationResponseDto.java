package com.titanic.airbnbclone.web.dto.response.accommodation;

import com.titanic.airbnbclone.domain.Reservation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountReservationResponseDto {

    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer people;
    private Integer totalPrice;

    @Builder
    public AccountReservationResponseDto(Long id, LocalDate startDate, LocalDate endDate,
                                         Integer people, Integer totalPrice) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.people = people;
        this.totalPrice = totalPrice;
    }

    public static AccountReservationResponseDto of(Reservation reservation) {
        return AccountReservationResponseDto.builder()
                .id(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .people(reservation.getClientCount())
                .totalPrice(reservation.getPrice())
                .build();
    }
}
