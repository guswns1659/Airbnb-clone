package com.titanic.airbnbclone.domain.accommodation;

import com.titanic.airbnbclone.domain.Reservation;
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

    public boolean isReservable(ReservationDemandDto reservationDemandDto) {

        for (Reservation reservation : this.reservations) {
            return reservation.validateReservation(reservationDemandDto);
        }
        return true;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setAccommodation(this);
    }
}
