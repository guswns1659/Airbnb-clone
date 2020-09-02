package com.titanic.airbnbclone.domain.account;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("host")
public class Host extends Account {

    @Override
    public Reservation addReservation(ReservationDemandDto reservationDemandDto) {
        Reservation reservation = Reservation.of(reservationDemandDto);
        this.getReservations().add(reservation);
        reservation.setAccount(this);
        return reservation;
    }
}
