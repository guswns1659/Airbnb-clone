package com.titanic.airbnbclone.utils;

import lombok.Getter;

@Getter
public enum OauthEnum {
    USER_EMAIL("userEmail"),  HEADER_LOCATION("Location"),
    MOBILE_REDIRECT_URL("issue04://?token="),
    HEADER_ACCEPT("Accept"), HEADER_MEDIA_TYPE("application/json"),
    AUTHORIZATION("Authorization"),
    OAUTH_URL_SERVER("https://github.com/login/oauth/authorize?client_id=bdd909bfff2137535182&redirect_uri=http://localhost:8080/callback&scope=user"),
    SECRET_KEY("airbnbClone"),
    TYP("typ"), TYP_VALUE("JWT"), ALG("HS256"), TOKEN("token"),
    OPTIONS("OPTIONS");

    private String value;

    OauthEnum(String value) {
        this.value = value;
    }
}
