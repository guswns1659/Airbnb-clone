package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.domain.account.Account;
import com.titanic.airbnbclone.domain.account.Member;
import com.titanic.airbnbclone.repository.AccountRepository;
import com.titanic.airbnbclone.utils.GithubProperties;
import com.titanic.airbnbclone.utils.OauthEnum;
import com.titanic.airbnbclone.web.dto.request.AccessTokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final GithubProperties githubProperties;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final WebClientService webClientService;

    public ResponseEntity<Void> login(String redirectCode,
                                      HttpServletResponse response) {

        githubProperties.addRedirectCode(redirectCode);
        AccessTokenRequest accessTokenRequest
                = AccessTokenRequest.of(githubProperties);

        try {
            // github에 accessToken 요청
            String rawAccessToken = webClientService.requestAccessTokenAtGithub(accessTokenRequest);
            String accessToken = parseRawAccessToken(rawAccessToken);

            // github에 유저 이메일 요청
            String email = webClientService.requestUserEmailAtGithub(accessToken);

            // 사용자 저장소에 persist
            Account member = Member.of(email);
            accountRepository.save(member);

            // 사용자 email로 jwt 토큰을 만든 뒤 쿠키로 response에 넣어서 리턴.
            addCookieToResponseServlet(response, email);
            return new ResponseEntity<>(HttpStatus.FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private void addCookieToResponseServlet(HttpServletResponse response, String email) {

        String jwtTokenWithEmail = jwtService.createJwtTokenWithEmail(email);
        log.info("jwtToken : {}", jwtTokenWithEmail);
        Cookie tokenCookie = new Cookie(OauthEnum.TOKEN.getValue(), jwtTokenWithEmail);
        Cookie userEmailCookie = new Cookie(OauthEnum.USER_EMAIL.getValue(), email);

        tokenCookie.setPath("/");
        userEmailCookie.setPath("/");
        response.addCookie(tokenCookie);
        response.addCookie(userEmailCookie);
        response.setHeader(OauthEnum.HEADER_LOCATION.getValue(), "http://15.164.35.235/githublogin");
    }

    private String parseRawAccessToken(String rawAccessToken) {
        String[] splitTokens = rawAccessToken.split("&");
        String[] splitTokens2 = splitTokens[0].split("=");
        return splitTokens2[1];
    }
}
