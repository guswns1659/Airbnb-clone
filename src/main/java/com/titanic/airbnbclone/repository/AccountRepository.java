package com.titanic.airbnbclone.repository;

import com.titanic.airbnbclone.domain.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
