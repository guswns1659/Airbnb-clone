package com.titanic.airbnbclone.domain.account;

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

    public static Member of(String email) {
        return Member.builder()
                .email(email)
                .build();
    }
}
