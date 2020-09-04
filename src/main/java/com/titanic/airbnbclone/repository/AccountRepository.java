package com.titanic.airbnbclone.repository;

import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.account.Account;
import com.titanic.airbnbclone.web.dto.request.accommodation.FilterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final EntityManager entityManager;

    public void save(Account account) {
        try {
            findByEmail(account.getEmail());
        } catch (Exception e) {
            entityManager.persist(account);
        }
    }

    public Account findByEmail(String email) {
        return entityManager.createQuery("select a from Account a where a.email = :email", Account.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public List<Accommodation> filterAccommodation(FilterRequestDto filterRequestDto) {
        if (filterRequestDto.getMin() == null) {
            return entityManager.createQuery("select distinct a from Accommodation a left join fetch a.reservations " +
                    "where a.location = :location " +
                    "and a.availableGuestCount >= :availableGuestCount", Accommodation.class)
                    .setParameter("location", filterRequestDto.getLocation())
                    .setParameter("availableGuestCount", filterRequestDto.getPeople())
                    .getResultList();
        } else {
            return entityManager.createQuery("select distinct a from Accommodation a left join fetch a.reservations " +
                    "where a.location = :location " +
                    "and a.availableGuestCount >= :availableGuestCount " +
                    "and a.currentPrice between :min and :max", Accommodation.class)
                    .setParameter("location", filterRequestDto.getLocation())
                    .setParameter("availableGuestCount", filterRequestDto.getPeople())
                    .setParameter("min", filterRequestDto.getMin())
                    .setParameter("max", filterRequestDto.getMax())
                    .getResultList();
        }
    }
}
