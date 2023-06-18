package com.account.accountbook.user.service.dto;

import com.account.accountbook.domain.UserRole;
import lombok.Getter;

@Getter
public class LoginResDto {

    private Long userId;
    private String token;
    private UserRole role;
    private String errorMessage;

    public void setLoginRes(Long userId, String token, UserRole role) {
        this.userId = userId;
        this.token = token;
        this.role = role;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
