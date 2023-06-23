package com.account.accountbook.user.repository;

import com.account.accountbook.domain.Member;
import com.account.accountbook.user.repository.dto.IsExistSocialLoginQueryRes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Member, Long>, UserRepositoryCustom{

    Optional<Member> getUserByEmail(@Param(value = "email") String email);

    Optional<Member> getUserByToken(@Param(value = "token") String token);

//    @Query(
//            value = "select new com.account.accountbook.user.repository.dto.IsExistSocialLoginQueryRes(m.id, m.token) from Member m" +
//            " where m.email = :email" +
//            " and m.name = :name" +
//            " and m.social_login_type = :type"
//            ,nativeQuery = true
//    )
//    Optional<IsExistSocialLoginQueryRes> isExistSocialUser(
//            @Param(value = "email") String email,
//            @Param(value = "name") String name,
//            @Param(value = "type") String type
//    );
}
