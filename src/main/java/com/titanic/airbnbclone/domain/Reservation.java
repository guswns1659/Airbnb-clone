package com.titanic.airbnbclone.domain;

import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.account.Account;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer clientCount;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
}
