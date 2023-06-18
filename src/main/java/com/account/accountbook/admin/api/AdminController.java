package com.account.accountbook.admin.api;

import com.account.accountbook.admin.api.dto.MemberInfoFormDto;
import com.account.accountbook.admin.api.dto.MemberSearchConditionDto;
import com.account.accountbook.admin.repository.dto.MemberDetailResDto;
import com.account.accountbook.admin.repository.dto.MemberEditResDto;
import com.account.accountbook.admin.repository.dto.MemberListResDto;
import com.account.accountbook.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {

    private final AdminService service;

    // 회원 목록
    @GetMapping("/api/user")
    public Page<MemberListResDto> searchMemberList(MemberSearchConditionDto conditionDto, Pageable pageable){
        return service.searchMemberList(conditionDto, pageable);
    }

    // 회원 상세보기
    @GetMapping("/api/user/{id}")
    public MemberDetailResDto getMemberInfo(@PathVariable("id") Long id){
        return service.getMemberInfo(id);
    }

    // 회원 수정
    @PatchMapping("/api/user/{id}")
    public MemberEditResDto updateMemberInfo(
            @PathVariable("id") Long id,
            @RequestBody MemberInfoFormDto form){

        return service.updateMemberInfo(form, id);
    }
}
