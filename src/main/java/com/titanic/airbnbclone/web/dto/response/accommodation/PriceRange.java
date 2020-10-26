package com.titanic.airbnbclone.web.dto.response.accommodation;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

/** JPA에서 엔티티매니저를 이용해 createNativeQuery를 요청할 때 응답되는 결과를 바로 POJO에 매핑을 하고 싶을 때 @SqlResultSetMapping을 사용
 *  그런데 위 어노테이션이 JPA2.1 이전에선 POJO는 안되고 엔티티에서만 사용할 수 있었습니다.
 *  2.1 버전 이후로 기본 POJO에도 매핑할 수 있도록 지원했습니다.
 *  하지만 그래도 바로 POJO로 사용할 수 없고 @MappedSuperClass로 추상클래스를 만들고
 *  이 클래스를 상속받는 POJO (이 프로젝트에선 PriceRangeResponseDto)에서 createNativeQuery의 결과를 매핑할 수 있습니다.
 */
@MappedSuperclass
@SqlResultSetMapping(name = "priceRangeResponseDto",
        classes = {@ConstructorResult(targetClass = PriceRangeResponse.class,
                columns = {@ColumnResult(name = "price", type = Integer.class),
                        @ColumnResult(name = "total", type = Integer.class)})
        })
public abstract class PriceRange {
}
