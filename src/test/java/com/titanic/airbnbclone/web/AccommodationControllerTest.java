package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.web.dto.response.accommodation.AccommodationResponseDtoList;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
@Transactional
public class AccommodationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private final static String REQUEST_MAPPING = "/accommodation";
    private final static String LOCALHOST = "http://localhost:";

    @ParameterizedTest
    @CsvSource({"30"})
    @Rollback(false)
    void 초기_숙박데이터30개를_요청한다(int expected) throws Exception {
        // given
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING + "/init";
        final String jwtToken = "eyJIUzI1NiI6IkhTMjU2IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJBdXRob3JpemF0aW9uIjoiZ3Vzd25zMTY1OUBnbWFpbC5jb20iLCJzdWIiOiJndXN3bnMxNjU5QGdtYWlsLmNvbSIsImV4cCI6MTU5OTAyMjk0OCwiaWF0IjoxNTk4OTM2NTQ4fQ.61XKtCMwq0EQpSNfs-Bm8poup9IP37DrlGEgQ2Y-nxg";
        final String OK = "200";

        // when
        AccommodationResponseDtoList responseDto = webTestClient.get()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), jwtToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(AccommodationResponseDtoList.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(responseDto.getStatus()).isEqualTo(OK);
        assertThat(responseDto.getAllData().size()).isEqualTo(expected);
    }
}
