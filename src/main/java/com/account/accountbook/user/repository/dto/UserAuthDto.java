package com.account.accountbook.user.repository.dto;

import com.account.accountbook.domain.UserRole;
import lombok.Getter;

@Getter
public class UserAuthDto {
    private Long id;
    private String profileUrl;
    private String name;
    private String tel;
    private String token;
    private UserRole role;
    private boolean isAuth;

    public UserAuthDto(Long id, String profileUrl, String name, String tel, String token, UserRole role, Boolean isAuth) {
        this.id = id;
        this.profileUrl = profileUrl;
        this.name = name;
        this.tel = tel;
        this.token = token;
        this.role = role;
        this.isAuth = isAuth;
    }
}
