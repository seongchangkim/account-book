package com.account.accountbook.admin.repository.dto;

import com.account.accountbook.domain.UserRole;
import lombok.Getter;

@Getter
public class MemberEditResDto {

    private String profileUrl;
    private String name;
    private String email;
    private String tel;
    private UserRole role;
    private boolean isEditSuccess;

    public MemberEditResDto(String profileUrl, String name, String email, String tel, UserRole role, boolean isEditSuccess) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.role = role;
        this.isEditSuccess = isEditSuccess;
    }
}
