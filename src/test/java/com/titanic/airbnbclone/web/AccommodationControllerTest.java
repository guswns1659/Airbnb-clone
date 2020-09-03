package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.utils.ReservationMessage;
import com.titanic.airbnbclone.utils.StatusEnum;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationDemandDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDtoList;
import com.titanic.airbnbclone.web.dto.response.accommodation.DeleteReservationResponseDto;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationInfoResponseDtoList;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationResponseDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
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
    void 초기_숙박데이터30개를_요청한다(int expected) {
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
    void 예약API를_테스트한다(LocalDate startDate, LocalDate endDate, int people, int totalPrice, long accommodationId) {

        final String successStatus = "200";

        // given
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING + "/" + accommodationId;
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
        assertThat(reservationResponseDto.getMessage()).isEqualTo(ReservationMessage.RESERVATION_SUCCESS.getMessage());
        assertThat(reservationResponseDto.getStatus()).isEqualTo(successStatus);
    }

    @ParameterizedTest
    @CsvSource({"1,10"})
    void 예약취소API를_테스트한다(long accommodationId, long reservationId) {
        // given
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING + "/" + accommodationId + "/" + reservationId;

        // when
        DeleteReservationResponseDto deleteReservationResponseDto = webTestClient.delete()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(DeleteReservationResponseDto.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(deleteReservationResponseDto.getMessage()).isEqualTo(ReservationMessage.RESERVATION_CANCEL_SUCCESS.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"1"})
    void 유저예약정보API를_테스트한다(int size) {
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING + "/" + "reservationInfo";

        // when
        ReservationInfoResponseDtoList reservationInfoResponseDtoList = webTestClient.get()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ReservationInfoResponseDtoList.class)
                .returnResult()
                .getResponseBody();
        // then
        assertThat(reservationInfoResponseDtoList.getStatus()).isEqualTo(StatusEnum.SUCCESS.getStatusCode());
        assertThat(reservationInfoResponseDtoList.getAllData().size()).isEqualTo(size);
    }
}
