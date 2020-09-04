package com.titanic.airbnbclone.domain.accommodation;

import com.titanic.airbnbclone.domain.Reservation;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
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

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setAccommodation(this);
    }

    // 각 숙박의 예약 객체에게 요청된 예약 날짜가 가능한지 물어보는 과정
    public boolean isReservable(LocalDate startDate, LocalDate endDate) {
        for (Reservation reservation : this.reservations) {
            if(reservation.validateReservation(startDate, endDate)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public boolean hasReservation() {
        return this.getReservations().size() != 0;
    }
}
