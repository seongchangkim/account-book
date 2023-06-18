package com.account.accountbook.user.repository;

import com.account.accountbook.user.repository.dto.MemberProfileResDto;

public interface UserRepositoryCustom {

    public MemberProfileResDto getProfileInfo(Long id);
}
