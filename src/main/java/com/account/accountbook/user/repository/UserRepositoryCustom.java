package com.account.accountbook.user.repository;

import com.account.accountbook.user.repository.dto.IsExistSocialLoginQueryRes;
import com.account.accountbook.user.repository.dto.MemberProfileResDto;

import java.util.Optional;

public interface UserRepositoryCustom {

    // 프로필 상세보기
    public MemberProfileResDto getProfileInfo(Long id);

    // 소셜로그인 회원 여부
    public Optional<IsExistSocialLoginQueryRes> isExistSocialUser(String email, String name, String type);
}
