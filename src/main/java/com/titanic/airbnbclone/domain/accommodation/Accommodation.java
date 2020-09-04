package com.titanic.airbnbclone.domain.accommodation;

import com.titanic.airbnbclone.domain.Reservation;
import com.titanic.airbnbclone.exception.AlreadyReservedException;
import com.titanic.airbnbclone.utils.ReservationMessage;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Accommodation {

    @Id
    @GeneratedValue
    @Column(name = "accommodation_id")
    private Long id;

    @Column(name = "accommodation_name")
    private String name;
    @Lob
    private String description;
    @Lob
    private String location;
    @Lob
    private String street;
    private Double latitude;
    private Double longitude;
    private int availableGuestCount;
    private int currentPrice;
    private int previousPrice;
    private int discountPrice;
    private double hotelRating;

    @OneToMany(mappedBy = "accommodation")
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    // 각 숙박의 예약 객체에게 요청된 예약 날짜가 가능한지 물어보는 과정
    public void isReservable(ReservationDemandDto reservationDemandDto) {
        for (Reservation reservation : this.reservations) {
            if(reservation.validateReservation(reservationDemandDto)) {
                continue;
            }
            throw new AlreadyReservedException(ReservationMessage.ALREADY_RESERVABLE.getMessage());
        }
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setAccommodation(this);
    }
}
