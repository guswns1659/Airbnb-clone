package com.titanic.airbnbclone.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class LoginControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private final static String REQUEST_MAPPING = "/github/oauth/callback2";
    private final static String LOCALHOST = "http://localhost:";

    @Test
    void 깃허브로그인_리다이렉션을_테스트한다() {

        // given
        String requestUrl = LOCALHOST + port + REQUEST_MAPPING;

        // when
        webTestClient.get()
                .uri(requestUrl)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FOUND)
                .expectBody(Void.class)
                .returnResult()
                .getResponseBody();

        // then
    }
}
