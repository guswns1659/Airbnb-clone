package com.titanic.airbnbclone.config;

import com.titanic.airbnbclone.utils.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String ALL = "*";
    private final String ALL_PATH = "/**";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedMethods = new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"};
        long MAX_AGE_SECS = 3600;

        registry.addMapping(ALL_PATH)
                .allowedOrigins(ALL)
                .allowedMethods(allowedMethods)
                .allowedHeaders(ALL)
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] excludePathPatterns = new String[]{"/github/**", "/init", "/filter", "/api/init", "/api/filter"};
        String[] swaggerPaths = new String[]{"/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**"};

        registry.addInterceptor(loginInterceptor())
                .addPathPatterns(ALL_PATH)
                .excludePathPatterns(excludePathPatterns)
                .excludePathPatterns(swaggerPaths);
    }
}
