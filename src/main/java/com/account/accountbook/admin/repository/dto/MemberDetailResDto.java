package com.account.accountbook.admin.repository.dto;

import com.account.accountbook.domain.UserRole;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberDetailResDto {

    private String profileUrl;
    private String name;
    private String email;
    private String tel;
    private UserRole role;

    @QueryProjection
    public MemberDetailResDto(String profileUrl, String name, String email, String tel, UserRole role) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.role = role;
    }
}
