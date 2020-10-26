package com.titanic.airbnbclone.domain.account;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationRequest;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("host")
public class Host extends Account {

    @Override
    public Reservation addReservation(ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.of(reservationRequest);
        this.getReservations().add(reservation);
        reservation.setAccount(this);
        return reservation;
    }
}
