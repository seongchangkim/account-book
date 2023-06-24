package com.account.accountbook.user.api;

import com.account.accountbook.common.CommonControllerFunc;
import com.account.accountbook.user.api.form.MemberProfileFormDto;
import com.account.accountbook.user.api.req.IsExistSocialMemberDto;
import com.account.accountbook.user.api.req.KakaoLoginReqDto;
import com.account.accountbook.user.api.form.LoginForm;
import com.account.accountbook.user.api.form.RegisterForm;
import com.account.accountbook.user.api.req.LogoutReqDto;
import com.account.accountbook.user.api.req.UserAuthReqDto;
import com.account.accountbook.domain.Member;
import com.account.accountbook.domain.SocialLoginType;
import com.account.accountbook.user.repository.dto.MemberProfileResDto;
import com.account.accountbook.user.repository.dto.MemberProfileUpdateResDto;
import com.account.accountbook.user.service.UserService;
import com.account.accountbook.user.repository.dto.IsExistSocialMemberResDto;
import com.account.accountbook.user.repository.dto.LoginResDto;
import com.account.accountbook.user.repository.dto.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final CommonControllerFunc commonControllerFunc;

    // 로그인
    @PostMapping("/api/user/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm form){

        try{
            LoginResDto res = userService.login(form.getEmail(), form.getPassword());

            return ResponseEntity.ok().body(res);
        }catch(Exception e){
            LoginResDto res = new LoginResDto();
            res.setErrorMessage(e.getMessage());

            return commonControllerFunc.getResponseDTO(res);
        }
    }

    // 회원가입
    @PostMapping("/api/user")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterForm form){
        Member member = new Member();
        member.registerMember(form.getEmail(), encoder.encode(form.getPassword()), form.getTel(), form.getName());

        Long userId = userService.register(member);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "회원가입 성공하셨습니다");
        result.put("userId", userId.toString());

        return commonControllerFunc.getResponseMap(result);
    }

    // 로그아웃
    @PostMapping("/api/user/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestBody LogoutReqDto req){
        String token = userService.logout(Long.parseLong(req.getUserId()));

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);

        return commonControllerFunc.getResponseMap(result);
    }

    // 로그인 여부
    @PostMapping("/api/user/auth")
    public UserAuthDto userAuth(@RequestBody UserAuthReqDto req){
        return userService.memberAuth(req.getToken());
    }

    // 카카오 로그인
    @PostMapping("/api/user/social-login/kakao")
    public void kakaoSignIn(@RequestBody KakaoLoginReqDto req){
        userService.socialLogin(req.getOauthToken(), Long.parseLong(req.getId()), SocialLoginType.KAKAO);
    }

    // 소셜 로그인 존재여부(해당 계정)
    @PostMapping("/api/user/social-login/exist")
    public ResponseEntity<Object> socialCheckUser(@RequestBody IsExistSocialMemberDto req){
        IsExistSocialMemberResDto result = userService.isExistSocialMember(req.getEmail(), req.getName(), req.getSocialType());

        return commonControllerFunc.getResponseDTO(result);
    }

    // 프로필 상세보기
    @GetMapping("/api/profile/{id}")
    public MemberProfileResDto getProfileInfo(@PathVariable("id") Long id){
        return userService.getProfileInfo(id);
    }

    // 프로필 수정
    @PatchMapping("/api/profile/{id}")
    public MemberProfileUpdateResDto updateProfileInfo(
            @PathVariable("id") Long id, @RequestBody MemberProfileFormDto form){
        return userService.updateProfileInfo(form, id);
    }

    // 회원 탈퇴 또는 삭제
    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<Map<String, Object>> leaveUser(@PathVariable("id") Long id) {
        Boolean isSuccess = userService.deleteMember(id);

        Map<String, Object> result = new HashMap<>();
        result.put("isSuccess", isSuccess);

        return commonControllerFunc.getResponseMap(result);
    }
}
