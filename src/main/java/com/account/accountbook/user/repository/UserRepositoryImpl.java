package com.account.accountbook.user.repository;

import com.account.accountbook.domain.SocialLoginType;
import com.account.accountbook.user.repository.dto.IsExistSocialLoginQueryRes;
import com.account.accountbook.user.repository.dto.MemberProfileResDto;
import com.account.accountbook.user.repository.dto.QIsExistSocialLoginQueryRes;
import com.account.accountbook.user.repository.dto.QMemberProfileResDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.account.accountbook.domain.QMember.member;
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberProfileResDto getProfileInfo(Long id) {
        return queryFactory.select(
                new QMemberProfileResDto(
                        member.profileUrl,
                        member.name,
                        member.tel
                )
        ).from(member)
         .where(member.id.eq(id)).fetchOne();
    }

    @Override
    public Optional<IsExistSocialLoginQueryRes> isExistSocialUser(String email, String name, String type) {
        return Optional.ofNullable(queryFactory.select(
                        new QIsExistSocialLoginQueryRes(
                                member.id,
                                member.token
                        )
                ).from(member)
                .where(member.email.eq(email), member.name.eq(name), member.socialLoginType.eq(SocialLoginType.valueOf(type))).fetchOne());
    }
}
