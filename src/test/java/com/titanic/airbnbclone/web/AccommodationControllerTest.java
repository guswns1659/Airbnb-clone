package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.utils.ReservationEnum;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDtoList;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationResponseDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
@Transactional
public class AccommodationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private final static String REQUEST_MAPPING = "/authorization";
    private final static String LOCALHOST = "http://localhost:";

    @ParameterizedTest
    @CsvSource({"30"})
    void 초기_숙박데이터30개를_요청한다(int expected) throws Exception {
        // given
        String requestUrl = LOCALHOST + port + "/init";
        final String OK = "200";

        // when
        AccommodationResponseDtoList responseDto = webTestClient.get()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(AccommodationResponseDtoList.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(responseDto.getStatus()).isEqualTo(OK);
        assertThat(responseDto.getAllData().size()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2020-09-10,2020-09-15,3,150000,1"})
    void 예약API를_테스트한다(LocalDate startDate, LocalDate endDate, int people, int totalPrice, String accommodationId) throws Exception {

        final String successStatus = "200";

        // given
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING + "/" +Long.parseLong(accommodationId);
        ReservationDemandDto reservationDemandDto = ReservationDemandDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .people(people)
                .totalPrice(totalPrice)
                .build();

        // when
        ReservationResponseDto reservationResponseDto = webTestClient.post()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .body(Mono.just(reservationDemandDto), ReservationDemandDto.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ReservationResponseDto.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(reservationResponseDto.getMessage()).isEqualTo(ReservationEnum.RESERVATION_SUCCESS.getMessage());
        assertThat(reservationResponseDto.getStatus()).isEqualTo(successStatus);
    }
}
