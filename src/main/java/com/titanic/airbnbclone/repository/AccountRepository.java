package com.titanic.airbnbclone.repository;

import com.titanic.airbnbclone.domain.account.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
