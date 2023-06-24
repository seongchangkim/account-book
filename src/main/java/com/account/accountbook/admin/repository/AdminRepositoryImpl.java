package com.account.accountbook.admin.repository;

import com.account.accountbook.admin.api.dto.MemberSearchConditionDto;
import com.account.accountbook.admin.repository.dto.MemberDetailResDto;
import com.account.accountbook.admin.repository.dto.MemberListResDto;
import com.account.accountbook.admin.repository.dto.QMemberDetailResDto;
import com.account.accountbook.admin.repository.dto.QMemberListResDto;
import com.account.accountbook.domain.SocialLoginType;
import com.account.accountbook.domain.UserRole;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.account.accountbook.domain.QMember.*;
import static org.springframework.util.StringUtils.hasText;

public class AdminRepositoryImpl implements AdminRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public AdminRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberListResDto> searchMemberList(MemberSearchConditionDto condition, Pageable pageable) {
        List<MemberListResDto> content = queryFactory.select(
                        new QMemberListResDto(
                                member.id,
                                member.email,
                                member.name,
                                member.profileUrl,
                                member.role,
                                dateFormat(member.time.createdAt),
                                dateFormat(member.time.updatedAt),
                                member.socialLoginType
                        )).from(member)
                .where(
                        nameContains(condition.getName()),
                        emailContains(condition.getEmail()),
                        roleEq(condition.getRole()),
                        socialTypeEq(condition.getSocialType())
                )
                .orderBy(member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();


        Long totalCount = queryFactory.select(member.count())
                .from(member)
                .where(
                        nameContains(condition.getName()),
                        emailContains(condition.getEmail()),
                        roleEq(condition.getRole()),
                        socialTypeEq(condition.getSocialType())
                ).fetchOne();

        return new PageImpl<>(content, pageable, totalCount);
    }

    // DB 전화번호 포멧 지정
    private StringExpression dateFormat(DateTimePath<Date> date) {
        return date.year().stringValue()
                .concat("-")
                .concat(date.month().stringValue())
                .concat("-")
                .concat(date.dayOfMonth().stringValue())
                .concat(" ")
                .concat(date.hour().stringValue())
                .concat(":")
                .concat(date.minute().stringValue())
                .concat(":")
                .concat(date.second().stringValue());
    }

    @Override
    public MemberDetailResDto getMemberInfo(Long id) {
        return queryFactory.select(
                new QMemberDetailResDto(
                        member.profileUrl,
                        member.name,
                        member.email,
                        member.tel,
                        member.role
                )
        ).from(member).where(member.id.eq(id)).fetchOne();
    }

    // 이름 카테고리
    private BooleanExpression nameContains(String name) {
        return hasText(name) ? member.name.contains(name) : null;
    }

    // 이메일 카테고리
    private BooleanExpression emailContains(String email) {
        return hasText(email) ? member.email.contains(email) : null;
    }

    // 회원 권한 카테고리
    private BooleanExpression roleEq(UserRole role) {
        return role != null ? member.role.eq(role) : null;
    }

    // 소셜 로그인 유형 카테고리
    private BooleanExpression socialTypeEq(SocialLoginType socialType) {
        return socialType != null ? member.socialLoginType.eq(socialType) : null;
    }
}
