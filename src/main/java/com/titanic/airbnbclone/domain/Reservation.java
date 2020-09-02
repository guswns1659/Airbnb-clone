package com.titanic.airbnbclone.domain;

import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.account.Account;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer clientCount;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Builder
    public Reservation(LocalDate startDate, LocalDate endDate, int clientCount, int price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientCount = clientCount;
        this.price = price;
    }

    public static Reservation of(ReservationDemandDto reservationDemandDto) {
        return Reservation.builder()
                .startDate(reservationDemandDto.getStartDate())
                .endDate(reservationDemandDto.getEndDate())
                .clientCount(reservationDemandDto.getPeople())
                .price(reservationDemandDto.getTotalPrice())
                .build();
    }

    public boolean validateReservation(ReservationDemandDto reservationDemandDto) {
        LocalDate requestStart = reservationDemandDto.getStartDate();
        LocalDate requestEnd = reservationDemandDto.getEndDate();

        // 기존 예약 날짜가 신규 예약 날짜 중간에 있는 경우
        if (newReserveContainOldReserve(requestStart, requestEnd)) {
            return false;
        }
        // 기존 예약 날짜와 신규 예약 날짜가 같은 경우
        if (newReserveEqualOldReserve(requestStart, requestEnd)) {
            return false;
        }
        // 기존 예약 날짜의 시작 날짜나 끝 날짜가 신규 예약 날짜와 겹칠 때
        if (newReserveOverlapOldReserve(requestStart, requestEnd)) {
            return false;
        }
        return true;
    }

    private boolean newReserveOverlapOldReserve(LocalDate requestStart, LocalDate requestEnd) {
        return (requestStart.isBefore(this.startDate) && requestEnd.isAfter(this.startDate))
                || (requestStart.isBefore(this.endDate) && requestEnd.isAfter(this.endDate));
    }

    private boolean newReserveEqualOldReserve(LocalDate requestStart, LocalDate requestEnd) {
        return (this.startDate.isEqual(requestStart) || this.endDate.isEqual(requestStart))
                || (this.startDate.isEqual(requestEnd) || this.endDate.isEqual(requestEnd));
    }

    private boolean newReserveContainOldReserve(LocalDate requestStart, LocalDate requestEnd) {
        return this.startDate.isBefore(requestStart) && this.endDate.isAfter(requestStart)
                || (this.startDate.isBefore(requestEnd) && this.endDate.isAfter(requestEnd));
    }
}
