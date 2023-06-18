package com.account.accountbook.user.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class IsExistSocialMemberResDto {

    private Long id;
    private boolean isExist;

    public IsExistSocialMemberResDto(Long id, boolean isExist) {
        this.id = id;
        this.isExist = isExist;
    }
}
