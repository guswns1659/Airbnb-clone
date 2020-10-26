package com.titanic.airbnbclone.repository;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.web.dto.response.accommodation.InitAccommodationResponse;
import com.titanic.airbnbclone.web.dto.response.accommodation.PriceRangeResponse;
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

    /** 페이징을 직접한 이유 : 1 : N관계에서 1을 조회할 때 join fetch 사용하고 페이징 처리를 하면 메모리에서 처리하기에 위험
     */
    public List<InitAccommodationResponse> getInitAccommodation() {
        String queryString = "select distinct a from Accommodation as a left join fetch a.pictures";
        List<Accommodation> accommodations = entityManager
                .createQuery(queryString, Accommodation.class)
                .getResultList();
        int startIndex = 0;
        int accommodationCount = 30;
        accommodations = accommodations.subList(startIndex, accommodationCount);

        return accommodations.stream()
                .map(InitAccommodationResponse::of)
                .collect(Collectors.toList());
    }

    public Optional<Accommodation> findById(Long accommodationId) {
        String queryString = "select distinct a from Accommodation as a left join fetch a.reservations where a.id = :accommodationId";
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

    public Optional<Accommodation> findOneWithPictures(Long accommodationId) {
        String queryString = "select distinct a from Accommodation as a left join fetch a.pictures where a.id = :accommodationId";
        Accommodation findAccommodation = entityManager
                .createQuery(queryString, Accommodation.class)
                .setParameter("accommodationId", accommodationId)
                .getSingleResult();

        return Optional.ofNullable(findAccommodation);
    }

    /** 요금 분포 그래프를 보여주기 위해 사용함.
     *  각 가격 범위별 가격을 지정하기 위해 case - when - then 사용.
     *  ex) 45000원 -> 10000원
     */
    public List<PriceRangeResponse> classifyAccommodationPrice() {

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

        // native 쿼리 결과를 바로 DTO 클래스에 매핑하기 위한 설정
        // 컴파일러에게 경고를 보내지 말라고 역할 : 검증되지 않는 연산자 관련 경고 억제
        @SuppressWarnings("unchecked")
        List<PriceRangeResponse> resultList = entityManager.createNativeQuery(sqlString, "priceRangeResponseDto")
                .getResultList();

        return resultList;
    }
}
