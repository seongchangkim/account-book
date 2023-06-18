package com.account.accountbook.user.repository.dto;

import lombok.Getter;

@Getter
public class MemberProfileUpdateResDto {
    private String profileUrl;
    private String name;
    private String tel;
    private boolean isSuccess;

    public MemberProfileUpdateResDto(String profileUrl, String name, String tel, boolean isSuccess) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.tel = tel;
        this.isSuccess = isSuccess;
    }
}
