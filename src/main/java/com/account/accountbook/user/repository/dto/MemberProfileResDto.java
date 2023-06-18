package com.account.accountbook.user.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberProfileResDto {

    private String profileUrl;
    private String name;
    private String tel;

    @QueryProjection
    public MemberProfileResDto(String profileUrl, String name, String tel) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.tel = tel;
    }
}
