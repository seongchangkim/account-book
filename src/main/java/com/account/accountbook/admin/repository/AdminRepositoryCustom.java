package com.account.accountbook.admin.repository;

import com.account.accountbook.admin.api.dto.MemberSearchConditionDto;
import com.account.accountbook.admin.repository.dto.MemberDetailResDto;
import com.account.accountbook.admin.repository.dto.MemberListResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminRepositoryCustom {
    Page<MemberListResDto> searchMemberList(MemberSearchConditionDto condition, Pageable pageable);

    MemberDetailResDto getMemberInfo(Long id);
}
