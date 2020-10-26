package com.titanic.airbnbclone.domain.account;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationRequest;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Member")
@NoArgsConstructor
public class Member extends Account{

    @Builder
    public Member(String email) {
        super(email);
    }

    @Override
    public Reservation addReservation(ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.of(reservationRequest);
        this.getReservations().add(reservation);
        reservation.setAccount(this);
        return reservation;
    }

    public static Member of(String email) {
        return Member.builder()
                .email(email)
                .build();
    }
}
