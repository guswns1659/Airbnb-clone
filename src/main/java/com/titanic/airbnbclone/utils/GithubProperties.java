package com.titanic.airbnbclone.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("github")
@Getter
@Setter
public class GithubProperties {

    @JsonProperty(value = "url")
    private String accessTokenRequestUrl;

    @JsonProperty(value = "client_id")
    private String clientId;

    @JsonProperty(value = "client_secret")
    private String clientSecret;

    @JsonProperty(value = "redirect_url")
    private String redirectUrl;

    private String redirectCode;

    public void addRedirectCode(String redirectCode) {
        this.redirectCode = redirectCode;
    }
}
