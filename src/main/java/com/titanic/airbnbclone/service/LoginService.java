package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.utils.GithubProperties;
import com.titanic.airbnbclone.web.dto.request.AccessTokenRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class LoginService {

    private final GithubProperties githubProperties;
    private WebClient webClient;

    public LoginService(GithubProperties githubProperties) {
        this.githubProperties = githubProperties;
        this.webClient = WebClient.create();
    }

    public ResponseEntity<Void> login(String redirectCode,
                                      HttpServletResponse response) {

        githubProperties.addRedirectCode(redirectCode);

        AccessTokenRequestDto accessTokenRequestDto
                = AccessTokenRequestDto.of(githubProperties);

        String accessToken = webClient.post()
                .uri(githubProperties.getAccessTokenRequestUrl())
                .body(Mono.just(accessTokenRequestDto), AccessTokenRequestDto.class)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        log.info("accessToken : {}", accessToken);

        return new ResponseEntity<>(HttpStatus.FOUND);
    }
}
