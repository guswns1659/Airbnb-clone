package com.titanic.airbnbclone.web.dto.response.accommodation;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

@MappedSuperclass
@SqlResultSetMapping(name = "priceRangeResponseDto",
        classes = {@ConstructorResult(targetClass = PriceRangeResponseDto.class,
                columns = {@ColumnResult(name = "price", type = Integer.class),
                        @ColumnResult(name = "total", type = Integer.class)})
        })
public abstract class PriceRange {
}
