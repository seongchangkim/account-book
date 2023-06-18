package com.account.accountbook.user.repository;

import com.account.accountbook.user.repository.dto.MemberProfileResDto;
import com.account.accountbook.user.repository.dto.QMemberProfileResDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.account.accountbook.domain.QMember.member;

@Repository
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


//    private final EntityManager em;
//
//    // 회원가입
//    public Long register(Member member){
//        em.persist(member);
//
//        return member.getId();
//    }
//
//    // 이메일로 해당 회원 조회
//    public Member getUserId(String email){
//        Member result = em.createQuery(
//                        "select m from Member m" +
//                                " where m.email = :email", Member.class)
//                .setParameter("email", email)
//                .getSingleResult();
//
//        return result;
//    }
//
//    // 회원 조회(회원 단위 테스트를 하기 위함)
//    public Member getMember(Long memberId){
//        return em.find(Member.class, memberId);
//    }
//
//    // 토큰으로 회원 정보 가져오기(회원 인증 기능 구현하기 위함)
//    public Member getMemberByToken(String token){
//        return em.createQuery("select m from Member m where token = :token", Member.class)
//                .setParameter("token", token)
//                .getSingleResult();
//    }
}
