package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.utils.OauthEnum;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final SignatureAlgorithm SIGNATUREALGORITHM = SignatureAlgorithm.HS256;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10;

    public String createJwtTokenWithEmail(String email) {
        Map<String, Object> header = new HashMap<>();
        header.put(OauthEnum.TYP.getValue(), OauthEnum.TYP_VALUE.getValue());
        header.put(OauthEnum.ALG.getValue(), OauthEnum.ALG.getValue());

        Map<String, Object> payload = new HashMap<>();
        payload.put(OauthEnum.AUTHORIZATION.getValue(), email);

        return Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNATUREALGORITHM, OauthEnum.SECRET_KEY.getValue())
                .compact();
    }

    public static String parseJwt(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(OauthEnum.SECRET_KEY.getValue())
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
