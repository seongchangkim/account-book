package com.account.accountbook.user.service;

import com.account.accountbook.config.JwtTokenProvider;
import com.account.accountbook.domain.Member;
import com.account.accountbook.domain.SocialLoginType;
import com.account.accountbook.user.api.form.MemberProfileFormDto;
import com.account.accountbook.user.exception.NotFoundMemberInfoException;
import com.account.accountbook.user.repository.UserRepository;
import com.account.accountbook.user.repository.dto.IsExistSocialLoginQueryRes;
import com.account.accountbook.user.repository.dto.MemberProfileResDto;
import com.account.accountbook.user.repository.dto.MemberProfileUpdateResDto;
import com.account.accountbook.user.service.dto.IsExistSocialMemberResDto;
import com.account.accountbook.user.service.dto.LoginResDto;
import com.account.accountbook.user.service.dto.UserAuthDto;
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
        Member loginMember = repository.getUserByEmail(email).orElseThrow(() -> new NotFoundMemberInfoException("입력한 아이디가 존재하지 않습니다"));

        if(!encoder.matches(password, loginMember.getPassword())){
            throw new NotFoundMemberInfoException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        String token = provider.registerToken(loginMember.getId());
        loginMember.updateToken(token);

        LoginResDto res = new LoginResDto();
        res.setLoginRes(loginMember.getId(), token, loginMember.getRole());

        return res;
    }

    // 로그아웃
    public String logout(Long userId){
        Member loginedMember = repository.findById(userId).orElseThrow(NotFoundMemberInfoException::new);

        LoginResDto res = new LoginResDto();
        loginedMember.updateToken(null);
        res.setLoginRes(userId, null, null);

        return res.getToken();
    }

    // 소셜 로그인
    public void socialLogin(String oauthToken, Long id, SocialLoginType type){
        Member member = repository.findById(id).orElseThrow(NotFoundMemberInfoException::new);

        member.updateTokenAndSocialLoginType(oauthToken, type);
    }

    // 회원 로그인 여부
    @Transactional(readOnly = true)
    public UserAuthDto memberAuth(String token){
        try{
            Member memberByToken = repository.getUserByToken(token).orElseThrow(NotFoundMemberInfoException::new);

            System.out.println("memberByToken = " + memberByToken.getId());
            UserAuthDto res = new UserAuthDto(
                    memberByToken.getId(),
                    memberByToken.getProfileUrl(),
                    memberByToken.getName(),
                    memberByToken.getTel(),
                    memberByToken.getToken(),
                    memberByToken.getRole(),
                    true);

            return res;
        }catch(Exception e){
            UserAuthDto res = new UserAuthDto(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    false);

            return res;
        }
    }

    @Transactional(readOnly = true)
    public IsExistSocialMemberResDto isExistSocialMember(String email, String name, String type){
        IsExistSocialLoginQueryRes existMember = repository.isExistSocialUser(email, name, type).orElse(null);
        boolean isExist = existMember != null ? true : false;
        System.out.println("existMember = " + existMember.getToken());
        System.out.println("existMember = " + existMember.getId());
        IsExistSocialMemberResDto res = new IsExistSocialMemberResDto(existMember.getId(), existMember.getToken(), isExist);

        return res;
    }

    public Boolean deleteMember(Long id){
        try{
            Member findMember = repository.findById(id).orElse(null);

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
        Member findMember = repository.findById(id).orElse(null);

        findMember.updateProfileInfo(form.getName(), form.getTel(), form.getProfileUrl(), new Date());

        MemberProfileUpdateResDto res = new MemberProfileUpdateResDto(form.getProfileUrl(), form.getName(), form.getTel(), true);

        return res;
    }
}
