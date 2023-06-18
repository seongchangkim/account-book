package com.account.accountbook.admin.api.dto;

import com.account.accountbook.domain.SocialLoginType;
import com.account.accountbook.domain.UserRole;
import lombok.Getter;

@Getter
public class MemberSearchConditionDto {

    private String name;
    private String email;
    private UserRole role;
    private SocialLoginType socialType;

    public MemberSearchConditionDto(String name, String email, UserRole role, SocialLoginType socialType) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.socialType = socialType;
    }
}
