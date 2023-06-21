package com.account.accountbook.admin.service;

import com.account.accountbook.admin.api.dto.MemberInfoFormDto;
import com.account.accountbook.admin.api.dto.MemberSearchConditionDto;
import com.account.accountbook.admin.repository.AdminRepository;
import com.account.accountbook.admin.repository.dto.MemberDetailResDto;
import com.account.accountbook.admin.repository.dto.MemberEditResDto;
import com.account.accountbook.admin.repository.dto.MemberListResDto;

import com.account.accountbook.domain.Member;
import com.account.accountbook.user.exception.NotFoundMemberInfoException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository repository;

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;

    public Page<MemberListResDto> searchMemberList(MemberSearchConditionDto req, Pageable pageable){
        return repository.searchMemberList(req, pageable);
    }

    public MemberDetailResDto getMemberInfo(Long id){
        return repository.getMemberInfo(id);
    }

    @Transactional
    public MemberEditResDto updateMemberInfo(MemberInfoFormDto form, Long id){
        Member findMember = repository.findById(id).orElseThrow(NotFoundMemberInfoException::new);

        findMember.updateUserInfo(form.getName(), form.getTel(), form.getRole(), form.getProfileUrl(), new Date());

        MemberEditResDto res = new MemberEditResDto(form.getProfileUrl(), form.getName(), form.getEmail(), form.getTel(), form.getRole(), true);
        return res;
    }
}
