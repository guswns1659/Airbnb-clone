package com.titanic.airbnbclone.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Accommodation {

    @Id
    @GeneratedValue
    @Column(name = "accommodation_id")
    private Long id;

    private String name;
    private String description;
    private String location;
    private String street;
    private Double latitude;
    private Double longitude;
    private Integer availableGuestCount;
    private Integer currentPrice;
    private Integer previousPrice;
    private Integer discountPrice;
    private String hotelRating;

    @OneToMany(mappedBy = "accommodation")
    private List<Picture> pictures = new ArrayList<>();
}
