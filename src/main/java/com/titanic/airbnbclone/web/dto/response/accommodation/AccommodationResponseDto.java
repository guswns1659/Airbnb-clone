package com.titanic.airbnbclone.web.dto.response.accommodation;

import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.accommodation.Picture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class AccommodationResponseDto {

    private Long id;
    private String hotelName;
    private String description;
    private String location;
    private String street;
    private Double lat;
    private Double lng;
    private Integer availableGuest;
    private Integer currentPrice;
    private Integer previousPrice;
    private String hotelRating;
    private List<Picture> urls;

    @Builder
    public AccommodationResponseDto(Long id, String hotelName, String description, String location, String street,
                                    Double lat, Double lng, Integer availableGuest, Integer currentPrice,
                                    Integer previousPrice, String hotelRating, List<Picture> urls) {
        this.id = id;
        this.hotelName = hotelName;
        this.description = description;
        this.location = location;
        this.street = street;
        this.lat = lat;
        this.lng = lng;
        this.availableGuest = availableGuest;
        this.currentPrice = currentPrice;
        this.previousPrice = previousPrice;
        this.hotelRating = hotelRating;
        this.urls = urls;
    }

    public static AccommodationResponseDto of(Accommodation accommodation) {
        return AccommodationResponseDto.builder()
                .id(accommodation.getId())
                .hotelName(accommodation.getName())
                .availableGuest(accommodation.getAvailableGuestCount())
                .currentPrice(accommodation.getCurrentPrice())
                .previousPrice(accommodation.getPreviousPrice())
                .description(accommodation.getDescription())
                .hotelRating(String.valueOf(accommodation.getHotelRating()))
                .lat(accommodation.getLatitude())
                .lng(accommodation.getLongitude())
                .location(accommodation.getLocation())
                .street(accommodation.getStreet())
                .urls(accommodation.getPictures())
                .build();
    }
}
