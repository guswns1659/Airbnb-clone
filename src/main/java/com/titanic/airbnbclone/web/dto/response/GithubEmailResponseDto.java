package com.titanic.airbnbclone.web.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GithubEmailResponseDto {

    private String email;
    private String primary;
    private String verified;
    private String visibility;
}
