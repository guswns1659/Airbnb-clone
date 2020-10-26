package com.titanic.airbnbclone.repository;

import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.account.Account;
import com.titanic.airbnbclone.web.dto.request.accommodation.FilterRequest;
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

    /** 동적쿼리를 위한 분기
     *  숙박 검색 시 요금 필드를 옵션이기 때문에 분기처리를 해줬다.
     *  QueryDsl에 대한 지식이 부족해 사용하지 못한 점 아쉬움.
     */
    public List<Accommodation> filterAccommodation(FilterRequest filterRequest) {
        if (filterRequest.getMin() == null) {
            return entityManager.createQuery("select distinct a from Accommodation a left join fetch a.reservations " +
                    "where a.location = :location " +
                    "and a.availableGuestCount >= :availableGuestCount", Accommodation.class)
                    .setParameter("location", filterRequest.getLocation())
                    .setParameter("availableGuestCount", filterRequest.getPeople())
                    .getResultList();
        } else {
            return entityManager.createQuery("select distinct a from Accommodation a left join fetch a.reservations " +
                    "where a.location = :location " +
                    "and a.availableGuestCount >= :availableGuestCount " +
                    "and a.currentPrice between :min and :max", Accommodation.class)
                    .setParameter("location", filterRequest.getLocation())
                    .setParameter("availableGuestCount", filterRequest.getPeople())
                    .setParameter("min", filterRequest.getMin())
                    .setParameter("max", filterRequest.getMax())
                    .getResultList();
        }
    }
}
