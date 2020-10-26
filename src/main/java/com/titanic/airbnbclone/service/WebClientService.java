package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.utils.GithubProperties;
import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.web.dto.request.AccessTokenRequest;
import com.titanic.airbnbclone.web.dto.response.GithubEmailResponse;
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

        GithubEmailResponse[] githubEmail = webClient.get()
                .uri(githubProperties.getEmailRequestUrl())
                .accept(MediaType.APPLICATION_JSON)
                .header(OauthEnum.AUTHORIZATION.getValue(), OauthEnum.TOKEN.getValue() + " " + accessToken)
                .retrieve()
                .bodyToMono(GithubEmailResponse[].class)
                .block();

        return githubEmail[0].getEmail();
    }

    public String requestAccessTokenAtGithub(AccessTokenRequest accessTokenRequest) {
        return webClient.post()
                .uri(githubProperties.getAccessTokenRequestUrl())
                .body(Mono.just(accessTokenRequest), AccessTokenRequest.class)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }
}
