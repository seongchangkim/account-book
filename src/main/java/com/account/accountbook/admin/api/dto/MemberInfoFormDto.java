package com.account.accountbook.admin.api.dto;

import com.account.accountbook.domain.UserRole;
import lombok.Getter;

@Getter
public class MemberInfoFormDto {

    private String email;
    private String profileUrl;
    private String name;
    private String tel;
    private UserRole role;
}
