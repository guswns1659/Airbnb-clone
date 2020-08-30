package com.titanic.airbnbclone.utils;

import com.titanic.airbnbclone.service.JwtService;
import org.apache.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (request.getMethod().equals(OauthEnum.OPTIONS.getValue())) {
            return true;
        }

        try {
            String userEmailInHeader = request.getHeader(OauthEnum.AUTHORIZATION.getValue());

            String jwtUserEmail = JwtService.parseJwt(userEmailInHeader);
            request.setAttribute(OauthEnum.USER_EMAIL.getValue(), jwtUserEmail);
            return true;
        } catch (Exception e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }
    }
}
