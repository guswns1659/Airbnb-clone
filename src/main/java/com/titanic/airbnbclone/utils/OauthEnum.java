package com.titanic.airbnbclone.utils;

import lombok.Getter;

@Getter
public enum OauthEnum {
    USER_EMAIL("userEmail"),  HEADER_LOCATION("Location"),
    HEADER_ACCEPT("Accept"), HEADER_MEDIA_TYPE("application/json"),
    AUTHORIZATION("Authorization"), MOBILE_REDIRECT_URL("issue04://?token="),
    SECRET_KEY("airbnbClone"), TYP("typ"), TYP_VALUE("JWT"), ALG("HS256"), TOKEN("token"),
    OPTIONS("OPTIONS"),
    OAUTH_URL_SERVER("https://github.com/login/oauth/authorize?client_id=bdd909bfff2137535182&redirect_uri=http://localhost:8080/callback&scope=user"),
    JWT_TOKEN_EXAMPLE("eyJIUzI1NiI6IkhTMjU2IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJBdXRob3JpemF0aW9uIjoiZ3Vzd25zMTY1OUBnbWFpbC5jb20iLCJzdWIiOiJndXN3bnMxNjU5QGdtYWlsLmNvbSIsImV4cCI6MTU5OTk2ODExMywiaWF0IjoxNTk5MTA0MTEzfQ.s3WGbolbFqjOXGZ45PBsNKZvIixkfLcrDuASpStOwfw");

    private String value;

    OauthEnum(String value) {
        this.value = value;
    }
}
