package com.titanic.airbnbclone.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.titanic.airbnbclone.utils.GithubProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenRequestDto {

    @JsonProperty(value = "client_id")
    private String clientId;

    @JsonProperty(value = "client_secret")
    private String clientSecret;

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "redirect_url")
    private String redirectUrl;

    @Builder
    public AccessTokenRequestDto(String clientId, String clientSecret, String code, String redirectUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.redirectUrl = redirectUrl;
    }

    public static AccessTokenRequestDto of(GithubProperties githubProperties) {
        return AccessTokenRequestDto.builder()
                .clientId(githubProperties.getClientId())
                .clientSecret(githubProperties.getClientSecret())
                .code(githubProperties.getRedirectCode())
                .redirectUrl(githubProperties.getRedirectUrl())
                .build();
    }
}
