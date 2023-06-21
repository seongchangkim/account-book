package com.account.accountbook.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long id;

    private String email;

    @Column(updatable = false)
    private String password;
    private String tel;
    private String profileUrl;
    private String name;
    private String token;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    private UserRole role;

    @Embedded
    private CommonTime time;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'NONE'")
    private SocialLoginType socialLoginType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Expense> expenses;


    // 회원 가입 로직(생성 메서드)
    public void registerMember(String email, String password, String tel, String name){
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.name = name;
    }

    // 로그인 기능 로직 : 토큰 갱신
    public void updateToken(String token){
        this.token = token;
    }

    // 소셜로그인 로직 : 토큰 갱신 및 socialType 설정
    public void updateTokenAndSocialLoginType(String token, SocialLoginType type){
        this.token = token;
        this.socialLoginType = type;
    }

    // 회원 수정 로직
    public void updateUserInfo(String name, String tel, UserRole role, String profileUrl, Date updatedAt){
        this.name = name;
        this.tel = tel;
        this.role = role;
        this.profileUrl = profileUrl;
        this.time.setUpdatedAt(updatedAt);
    }

    // 프로필 정보 수정 로직
    public void updateProfileInfo(String name, String tel, String profileUrl, Date updatedAt){
        this.name = name;
        this.tel = tel;
        this.profileUrl = profileUrl;
        this.time.setUpdatedAt(updatedAt);
    }
}
