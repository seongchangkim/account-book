package com.account.accountbook.user.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class IsExistSocialLoginQueryRes {

    private Long id;
    private String token;

    @QueryProjection
    public IsExistSocialLoginQueryRes(Long id, String token) {
        this.id = id;
        this.token = token;
    }
}
