package com.titanic.airbnbclone.web.dto.request.accommodation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterRequestDto {

    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer people;
    private Integer min;
    private Integer max;

    @Builder
    public FilterRequestDto(String location, LocalDate startDate, LocalDate endDate, Integer people, Integer min, Integer max) {
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.people = people;
        this.min = min;
        this.max = max;
    }
}
