package com.titanic.airbnbclone.domain.account;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
@Getter
public abstract class Account {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_email", unique = true)
    private String email;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Account(String email) {
        this.email = email;
    }

    public abstract Reservation addReservation(ReservationDemandDto reservationDemandDto);
}
