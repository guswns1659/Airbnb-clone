package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.utils.GithubProperties;
import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.web.dto.request.AccessTokenRequestDto;
import com.titanic.airbnbclone.web.dto.response.GithubEmailResponseDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    private WebClient webClient;
    private GithubProperties githubProperties;

    public WebClientService(GithubProperties githubProperties) {
        this.webClient = WebClient.create();
        this.githubProperties = githubProperties;
    }

    public String requestUserEmailAtGithub(String accessToken) {

        GithubEmailResponseDto[] githubEmail = webClient.get()
                .uri(githubProperties.getEmailRequestUrl())
                .accept(MediaType.APPLICATION_JSON)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.TOKEN.getValue() + " " + accessToken)
                .retrieve()
                .bodyToMono(GithubEmailResponseDto[].class)
                .log()
                .block();

        return githubEmail[0].getEmail();
    }

    public String requestAccessTokenAtGithub(AccessTokenRequestDto accessTokenRequestDto) {
        return webClient.post()
                .uri(githubProperties.getAccessTokenRequestUrl())
                .body(Mono.just(accessTokenRequestDto), AccessTokenRequestDto.class)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }
}
