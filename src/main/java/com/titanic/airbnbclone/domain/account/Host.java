package com.titanic.airbnbclone.domain.account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("host")
public class Host extends Account {
}
