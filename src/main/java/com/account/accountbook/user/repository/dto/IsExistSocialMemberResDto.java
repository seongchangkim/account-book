package com.account.accountbook.user.repository.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class IsExistSocialMemberResDto {

    private Long id;
    private String token;
    private boolean isExist;

    public IsExistSocialMemberResDto(Long id, String token, boolean isExist) {
        this.id = id;
        this.token = token;
        this.isExist = isExist;
    }
}
