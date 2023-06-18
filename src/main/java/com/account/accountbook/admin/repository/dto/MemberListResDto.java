package com.account.accountbook.admin.repository.dto;

import com.account.accountbook.domain.SocialLoginType;
import com.account.accountbook.domain.UserRole;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberListResDto {

    private Long id;
    private String email;
    private String name;
    private String profileUrl;
    private UserRole role;
    private String createdAt;
    private String updatedAt;
    private SocialLoginType socialType;

    @QueryProjection

    public MemberListResDto(
            Long id,
            String email,
            String name,
            String profileUrl,
            UserRole role,
            String createdAt,
            String updatedAt,
            SocialLoginType socialType) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.socialType = socialType;
    }
}
