package com.account.accountbook.user.service;

import com.account.accountbook.config.JwtTokenProvider;
import com.account.accountbook.domain.Member;
import com.account.accountbook.domain.SocialLoginType;
import com.account.accountbook.domain.UserRole;
import com.account.accountbook.user.api.form.MemberProfileFormDto;
import com.account.accountbook.user.exception.NotFoundMemberInfoException;
import com.account.accountbook.user.repository.UserRepository;
import com.account.accountbook.user.repository.dto.IsExistSocialLoginQueryRes;
import com.account.accountbook.user.repository.dto.MemberProfileResDto;
import com.account.accountbook.user.repository.dto.MemberProfileUpdateResDto;
import com.account.accountbook.user.repository.dto.IsExistSocialMemberResDto;
import com.account.accountbook.user.repository.dto.LoginResDto;
import com.account.accountbook.user.repository.dto.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    public final UserRepository repository;
    public final PasswordEncoder encoder;
    public final JwtTokenProvider provider;

    public Long register(Member member){
        Member savedMember = repository.save(member);

        return savedMember.getId();
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMember(Long memberId){
        return repository.findById(memberId);
    }

    // 로그인
    public LoginResDto login(String email, String password){
        Member loginMember = repository.getUserByEmail(email).orElse(null);

        isExistFindMember(loginMember);

        if(!encoder.matches(password, loginMember.getPassword())){
            throw new NotFoundMemberInfoException("비밀번호가 일치하지 않습니다");
        }

        String token = provider.registerToken(loginMember.getId());
        loginMember.updateToken(token);

        return updateLoginDto(loginMember.getId(), token, loginMember.getRole());
    }

    // 로그아웃
    public String logout(Long userId){
        Member loginedMember = findMemberById(userId);

        isExistFindMember(loginedMember);

        loginedMember.updateToken(null);
        LoginResDto res = updateLoginDto(userId, null, null);

        return res.getToken();
    }

    // 소셜 로그인
    public void socialLogin(String oauthToken, Long id, SocialLoginType type){
        Member findMember = findMemberById(id);

        isExistFindMember(findMember);

        findMember.updateTokenAndSocialLoginType(oauthToken, type);
    }

    // 회원 로그인 여부
    @Transactional(readOnly = true)
    public UserAuthDto memberAuth(String token){

            Member findMemberByToken = repository.getUserByToken(token).orElse(null);

            if(findMemberByToken == null){
                return new UserAuthDto(null, null, null, null, null, null, false);
            }else{
                return new UserAuthDto(
                        findMemberByToken.getId(),
                        findMemberByToken.getProfileUrl(),
                        findMemberByToken.getName(),
                        findMemberByToken.getTel(),
                        findMemberByToken.getToken(),
                        findMemberByToken.getRole(),
                        true);
            }
    }

    @Transactional(readOnly = true)
    public IsExistSocialMemberResDto isExistSocialMember(String email, String name, String type){
        IsExistSocialLoginQueryRes existMember = repository.isExistSocialUser(email, name, type).orElse(null);
        boolean isExist = existMember != null ? true : false;

        return new IsExistSocialMemberResDto(existMember.getId(), existMember.getToken(), isExist);
    }

    public Boolean deleteMember(Long id){
        try{
            Member findMember = findMemberById(id);

            isExistFindMember(findMember);

            // 로그인 되어 있으면
            if(findMember.getToken() != null){
                findMember.updateToken(null);
            }

            repository.deleteById(id);

            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Transactional(readOnly = true)
    public MemberProfileResDto getProfileInfo(Long id){
        return repository.getProfileInfo(id);
    }

    public MemberProfileUpdateResDto updateProfileInfo(MemberProfileFormDto form, Long id){
        Member findMember = findMemberById(id);

        isExistFindMember(findMember);

        findMember.updateProfileInfo(form.getName(), form.getTel(), form.getProfileUrl(), new Date());

        return new MemberProfileUpdateResDto(form.getProfileUrl(), form.getName(), form.getTel(), true);
    }

    // userId에 따른 회원 조회(공통 메소드)
    private Member findMemberById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // 로그인 response 값 수정
    private static LoginResDto updateLoginDto(Long userId, String token, UserRole role) {
        LoginResDto res = new LoginResDto();
        res.setLoginRes(userId, token, role);
        return res;
    }

    // 조회된 회원이 존재하는지 확인
    private static void isExistFindMember(Member loginMember) {
        if(loginMember == null){
            throw new NotFoundMemberInfoException("해당 회원이 존재하지 않습니다");
        }
    }
}
