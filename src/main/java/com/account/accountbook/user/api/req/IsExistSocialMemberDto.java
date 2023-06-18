package com.account.accountbook.user.api.req;

import lombok.Getter;

@Getter
public class IsExistSocialMemberDto {
    private String email;
    private String name;
    private String socialType;
}
