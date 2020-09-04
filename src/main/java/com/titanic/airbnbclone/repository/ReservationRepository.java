package com.titanic.airbnbclone.repository;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.exception.CancelFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager entityManager;

    public void isExisted(Long reservationId) {
        Optional.ofNullable(entityManager.find(Reservation.class, reservationId))
            .orElseThrow(CancelFailException::new);
    }
}
