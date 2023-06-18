package com.account.accountbook.admin.service;

import com.account.accountbook.admin.api.dto.MemberSearchConditionDto;
import com.account.accountbook.admin.repository.dto.MemberDetailResDto;
import com.account.accountbook.admin.repository.dto.MemberListResDto;
import com.account.accountbook.domain.Member;
import com.account.accountbook.domain.SocialLoginType;
import com.account.accountbook.domain.UserRole;
import com.account.accountbook.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminServiceTest {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    EntityManager em;

    @Test
    public void 회원_목록_페이징_처리() throws Exception{
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();
        Member member4 = new Member();
        Member member5 = new Member();
        member1.registerMember("test@test.com", encoder.encode("test1234"), "010-0000-0000", "test");
        member1.registerMember("test1@test.com", encoder.encode("test1111"), "010-1111-1111", "test1");
        member1.registerMember("test2@test.com", encoder.encode("test2222"), "010-2222-2222", "test2");
        member1.registerMember("test3@test.com", encoder.encode("test3333"), "010-3333-3333", "test3");
        member1.registerMember("test4@test.com", encoder.encode("test4444"), "010-4444-4444", "test4");
//        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        userService.register(member1);
        userService.register(member2);
        userService.register(member3);
        userService.register(member4);
        userService.register(member5);

        //when
        MemberSearchConditionDto condition = new MemberSearchConditionDto("", "", UserRole.USER, SocialLoginType.NONE);

        PageRequest req = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "member_id"));
        Page<MemberListResDto> page = adminService.searchMemberList(condition, req);
        List<MemberListResDto> content = page.getContent();

        //then
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(content.size()).isEqualTo(4);
    }

    @Test
    public void 해당_회원_상세보기() throws Exception{
        //given
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-0000-0000", "test");
        userService.register(member);

        //when
        MemberDetailResDto memberInfoRes = adminService.getMemberInfo(member.getId());

        //then
        assertThat(memberInfoRes.getEmail()).isEqualTo(member.getEmail());
        assertThat(memberInfoRes.getName()).isEqualTo(member.getName());
        assertThat(memberInfoRes.getProfileUrl()).isEqualTo(member.getProfileUrl());
        assertThat(memberInfoRes.getRole()).isEqualTo(UserRole.USER);
        assertThat(memberInfoRes.getTel()).isEqualTo(member.getTel());
    }
}