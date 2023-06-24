package com.account.accountbook.user.service;

import com.account.accountbook.config.JwtTokenProvider;
import com.account.accountbook.domain.Member;
import com.account.accountbook.user.exception.NotFoundMemberInfoException;
import com.account.accountbook.user.repository.dto.MemberProfileResDto;
import com.account.accountbook.user.repository.dto.LoginResDto;
import com.account.accountbook.user.repository.dto.UserAuthDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService service;
    @Autowired PasswordEncoder encoder;
    @Autowired JwtTokenProvider jwtTokenProvider;

    @Test
    public void 회원가입() throws Exception{
        //given
//        Member member = new Member("test@test.com", "test1234", "010-1111-1111", "test");
        Member member = new Member();
        member.registerMember("test@test.com", "test1234", "010-1111-1111", "test");
        Long registeredId = service.register(member);

        //when
        Member findMember = service.getMember(registeredId).get();

        //then
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(findMember.getTel()).isEqualTo(member.getTel());
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    public void 로그인() throws Exception{
        //given
//        Member member = new Member("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        service.register(member);

        LoginResDto res = service.login("test@test.com", "test1234");

        //when
        Member findMember = service.getMember(res.getUserId()).get();

        //then
        assertThat(findMember.getId()).isEqualTo(res.getUserId());
        assertThat(findMember.getToken()).isEqualTo(res.getToken());

    }

    @Test
    public void 아이디_또는_비밀번호_불일치() throws Exception{
        //given
//        Member member = new Member("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        service.register(member);

        //when

        //then
        assertThrows(NotFoundMemberInfoException.class, () -> {
            LoginResDto res = service.login("test@test.com", "test1235");
        },"예외가 발생하지 않았습니다!");
    }

    @Test
    public void 로그아웃() throws Exception{
        //given
//        Member member = new Member("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        service.register(member);

        LoginResDto loginRes = service.login("test@test.com", "test1234");

        //when
        String logoutedToken = service.logout(loginRes.getUserId());

        //then
        assertThat(logoutedToken).isEqualTo("");
    }

    @Test
    public void 로그인_여부_확인_성공() throws Exception{
        //given
        //회원가입
//        Member member = new Member("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        service.register(member);

        // 로그인
        LoginResDto res = service.login("test@test.com", "test1234");

        //when
        // 로그인 여부
        UserAuthDto userAuth = service.memberAuth(res.getToken());

        // 회원 상세보기
        Member findMember = service.getMember(res.getUserId()).get();

        //then
        assertThat(userAuth.getId()).isEqualTo(findMember.getId());
        assertThat(userAuth.getToken()).isEqualTo(findMember.getToken());
        assertThat(userAuth.getProfileUrl()).isEqualTo(findMember.getProfileUrl());
        assertThat(userAuth.getTel()).isEqualTo(findMember.getTel());
        assertThat(userAuth.getName()).isEqualTo(findMember.getName());
        assertThat(userAuth.getRole()).isEqualTo(findMember.getRole());
        assertThat(userAuth.isAuth()).isEqualTo(true);
    }

    @Test
    public void 로그인_여부_확인_실패() throws Exception{
        //given
        //회원가입
//        Member member = new Member("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");

        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        service.register(member);

        //when
        // 로그인 실패
        assertThrows(NotFoundMemberInfoException.class, () -> {
            LoginResDto res = service.login("test@test.com", "test1235");

            // 로그인 여부
            UserAuthDto userAuth = service.memberAuth(res.getToken());

            //then
            assertThat(userAuth.getId()).isEqualTo(null);
            assertThat(userAuth.getToken()).isEqualTo(null);
            assertThat(userAuth.getProfileUrl()).isEqualTo(null);
            assertThat(userAuth.getTel()).isEqualTo(null);
            assertThat(userAuth.getName()).isEqualTo(null);
            assertThat(userAuth.getRole()).isEqualTo(null);
            assertThat(userAuth.isAuth()).isEqualTo(false);
        },"예외가 발생하지 않았습니다!");
    }

    @Test
    public void 회원_탈퇴() throws Exception{
        //given
        //회원가입
//        Member member = new Member("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");

        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Long registeredId = service.register(member);

        //when
        System.out.println("registeredId = " + registeredId);

        // then
        boolean isSuccess = service.deleteMember(registeredId);
        System.out.println("isSuccess = " + isSuccess);

        assertThat(isSuccess).isTrue();
    }
    
    @Test
    public void 프로필_상세보기() throws Exception{
        //given
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Long registeredId = service.register(member);

        //when
        MemberProfileResDto res = service.getProfileInfo(registeredId);

        //then
        assertThat(res.getProfileUrl()).isEqualTo(member.getProfileUrl());
        assertThat(res.getTel()).isEqualTo(member.getTel());
        assertThat(res.getName()).isEqualTo(member.getName());
    }
}