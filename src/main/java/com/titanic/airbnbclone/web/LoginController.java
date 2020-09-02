package com.titanic.airbnbclone.web;

import com.titanic.airbnbclone.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("github")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<Void> login(@RequestParam("code") String redirectCode,
                                      HttpServletResponse response) {
        return loginService.login(redirectCode, response);
    }
}
