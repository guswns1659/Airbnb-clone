package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.utils.ReservationMessage;
import com.titanic.airbnbclone.utils.StatusEnum;
import com.titanic.airbnbclone.web.dto.request.accommodation.FilterRequest;
import com.titanic.airbnbclone.web.dto.request.accommodation.ReservationRequest;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseList;
import com.titanic.airbnbclone.web.dto.response.accommodation.DeleteReservationResponse;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationInfoResponseList;
import com.titanic.airbnbclone.web.dto.response.accommodation.ReservationResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.stream.Stream;

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
        AccommodationResponseList responseDto = webTestClient.get()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(AccommodationResponseList.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(responseDto.getStatus()).isEqualTo(OK);
        assertThat(responseDto.getAllData().size()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2020-01-10,2020-01-15,3,150000,1"})
    void 예약API를_테스트한다(LocalDate startDate, LocalDate endDate, int people, int totalPrice, long accommodationId) {

        final String successStatus = "200";

        // given
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING + "/" + accommodationId;
        ReservationRequest reservationRequest = ReservationRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .people(people)
                .totalPrice(totalPrice)
                .build();

        // when
        ReservationResponse reservationResponse = webTestClient.post()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .body(Mono.just(reservationRequest), ReservationRequest.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ReservationResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(reservationResponse.getMessage()).isEqualTo(ReservationMessage.RESERVATION_SUCCESS.getMessage());
        assertThat(reservationResponse.getStatus()).isEqualTo(successStatus);
    }

    @ParameterizedTest
    @CsvSource({"1,12"})
    void 예약취소API를_테스트한다(long accommodationId, long reservationId) {
        // given
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING + "/" + accommodationId + "/" + reservationId;

        // when
        DeleteReservationResponse deleteReservationResponse = webTestClient.delete()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(DeleteReservationResponse.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(deleteReservationResponse.getMessage()).isEqualTo(ReservationMessage.RESERVATION_CANCEL_FAIL.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"0"})
    void 유저예약정보API를_테스트한다(int size) {
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING + "/" + "reservationInfo";

        // when
        ReservationInfoResponseList reservationInfoResponseList = webTestClient.get()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ReservationInfoResponseList.class)
                .returnResult()
                .getResponseBody();
        // then
        assertThat(reservationInfoResponseList.getStatus()).isEqualTo(StatusEnum.SUCCESS.getStatusCode());
        assertThat(reservationInfoResponseList.getAllData().size()).isEqualTo(size);
    }

    public static Stream<Arguments> filterRequestDto() {
        FilterRequest filterRequest = FilterRequest.builder()
                .location("Seattle")
                .startDate(LocalDate.of(2020, 8, 1))
                .endDate(LocalDate.of(2020, 8, 31))
                .people(3)
                .build();

        return Stream.of(
                Arguments.of(filterRequest, 93)
        );
    }

    @ParameterizedTest
    @MethodSource("filterRequestDto")
    void 숙박필터링API를_테스트한다(FilterRequest filterRequest, int size) {

        // given
        String requestUrl = LOCALHOST + port + "/filter";

        // when
        AccommodationResponseList accommodationResponseList = webTestClient.post()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.JWT_TOKEN_EXAMPLE.getValue())
                .body(Mono.just(filterRequest), FilterRequest.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(AccommodationResponseList.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(accommodationResponseList.getAllData().size()).isEqualTo(size);
    }
}
