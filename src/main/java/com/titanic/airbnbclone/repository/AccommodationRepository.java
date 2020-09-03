package com.titanic.airbnbclone.repository;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.PriceRangeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccommodationRepository {

    private final EntityManager entityManager;
    private final AccountRepository accountRepository;

    public List<PriceRangeResponseDto> classifyAccommodationPrice() {

        String sqlString = "select price, count(*) as total from (\n" +
                "select current_price,\n" +
                "case\n" +
                "    when current_price < 50000 then 10000\n" +
                "    when current_price < 100000 then 50000\n" +
                "    when current_price < 150000 then 100000\n" +
                "    when current_price < 200000 then 150000\n" +
                "    when current_price < 250000 then 200000\n" +
                "    when current_price < 300000 then 250000\n" +
                "    when current_price < 350000 then 300000\n" +
                "    when current_price < 400000 then 350000\n" +
                "    when current_price < 450000 then 400000\n" +
                "    when current_price < 500000 then 450000\n" +
                "    when current_price < 550000 then 500000\n" +
                "    when current_price < 600000 then 550000\n" +
                "    when current_price < 650000 then 600000\n" +
                "    when current_price < 700000 then 650000\n" +
                "    when current_price < 750000 then 700000\n" +
                "    when current_price < 800000 then 750000\n" +
                "    when current_price < 850000 then 800000\n" +
                "    when current_price < 900000 then 850000\n" +
                "    when current_price < 950000 then 900000\n" +
                "    else 950000\n" +
                "end as price\n" +
                "from accommodation)\n" +
                "accommodation group by price order by price;";

        @SuppressWarnings("unchecked")
        List<PriceRangeResponseDto> resultList = entityManager.createNativeQuery(sqlString, "priceRangeResponseDto")
                .getResultList();

        return resultList;
    }

    public List<AccommodationResponseDto> getInitAccommodation() {
        String queryString = "select distinct a from Accommodation as a left join fetch a.pictures";
        List<Accommodation> accommodations = entityManager
                .createQuery(queryString, Accommodation.class)
                .getResultList();
        accommodations = accommodations.subList(0, 30);

        return accommodations.stream()
                .map(AccommodationResponseDto::of)
                .collect(Collectors.toList());
    }

    public Optional<Accommodation> findOne(Long accommodationId) {
        String queryString = "select distinct a from Accommodation as a left join fetch a.pictures " +
                "left join fetch a.reservations where a.id = :accommodationId";
        Accommodation findAccommodation = entityManager
                .createQuery(queryString, Accommodation.class)
                .setParameter("accommodationId", accommodationId)
                .getSingleResult();

        return Optional.ofNullable(findAccommodation);
    }

    public void save(Accommodation findAccommodation) {
        entityManager.persist(findAccommodation);
    }

    public void cancelReservation(Long reservationId) {
        entityManager.remove(entityManager.find(Reservation.class, reservationId));
    }
}
