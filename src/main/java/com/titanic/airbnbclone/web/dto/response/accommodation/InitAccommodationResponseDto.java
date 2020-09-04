package com.titanic.airbnbclone.web.dto.response.accommodation;

import com.titanic.airbnbclone.domain.accommodation.Accommodation;
import com.titanic.airbnbclone.domain.accommodation.Picture;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InitAccommodationResponseDto {

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

    public static InitAccommodationResponseDto of(Accommodation accommodation) {
        return InitAccommodationResponseDto.builder()
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
                .urls(accommodation.getPictures().subList(0,1))
                .build();
    }
}
