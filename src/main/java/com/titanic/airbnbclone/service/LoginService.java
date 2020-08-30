package com.titanic.airbnbclone.service;

import com.titanic.airbnbclone.domain.account.Account;
import com.titanic.airbnbclone.domain.account.Member;
import com.titanic.airbnbclone.repository.AccountRepository;
import com.titanic.airbnbclone.utils.GithubProperties;
import com.titanic.airbnbclone.web.dto.request.AccessTokenRequestDto;
import com.titanic.airbnbclone.web.dto.response.GithubEmailResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class LoginService {

    private final GithubProperties githubProperties;
    private WebClient webClient;
    private final AccountRepository accountRepository;

    public LoginService(GithubProperties githubProperties, AccountRepository accountRepository) {
        this.githubProperties = githubProperties;
        this.accountRepository = accountRepository;
        this.webClient = WebClient.create();
    }

    @Transactional
    public ResponseEntity<Void> login(String redirectCode,
                                      HttpServletResponse response) {

        githubProperties.addRedirectCode(redirectCode);

        AccessTokenRequestDto accessTokenRequestDto
                = AccessTokenRequestDto.of(githubProperties);

        try {


            // github에 accessToken 요청
            String rawAccessToken = webClient.post()
                    .uri(githubProperties.getAccessTokenRequestUrl())
                    .body(Mono.just(accessTokenRequestDto), AccessTokenRequestDto.class)
                    .exchange()
                    .block()
                    .bodyToMono(String.class)
                    .block();

            String accessToken = parseRawAccessToken(rawAccessToken);

            // github에 유저 이메일 요청
            GithubEmailResponseDto[] githubEmail = webClient.get()
                    .uri(githubProperties.getEmailRequestUrl())
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "token " + accessToken)
                    .retrieve()
                    .bodyToMono(GithubEmailResponseDto[].class)
                    .log()
                    .block();

            String email = githubEmail[0].getEmail();

            // 사용자 저장소에 persist
            Account member = Member.builder()
                    .email(email)
                    .build();

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

//        String jwtToken = JwtUtils.jwt

    }

    private String parseRawAccessToken(String rawAccessToken) {
        String[] splitTokens = rawAccessToken.split("&");
        String[] splitTokens2 = splitTokens[0].split("=");
        return splitTokens2[1];
    }
}
