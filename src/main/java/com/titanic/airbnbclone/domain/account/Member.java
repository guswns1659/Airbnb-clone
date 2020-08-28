package com.titanic.airbnbclone.domain.account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Member")
public class Member extends Account{
}
