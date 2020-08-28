package com.titanic.airbnbclone.domain.account;

import com.titanic.airbnbclone.domain.Reservation;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Account {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String email;

    @OneToMany(mappedBy = "account")
    private List<Reservation> reservations = new ArrayList<>();

}
