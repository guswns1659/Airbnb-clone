package com.titanic.airbnbclone.domain.account;

import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Account {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String email;
//    private List<Reservation>

}
