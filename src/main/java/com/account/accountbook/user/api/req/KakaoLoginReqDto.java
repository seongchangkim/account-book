package com.account.accountbook.user.api.req;

import lombok.Getter;

@Getter
public class KakaoLoginReqDto {
    private String id;
    private String oauthToken;
}
