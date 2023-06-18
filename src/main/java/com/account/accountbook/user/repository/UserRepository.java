package com.account.accountbook.user.repository;

import com.account.accountbook.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Member, Long>, UserRepositoryCustom{

    Optional<Member> getUserByEmail(@Param(value = "email") String email);

    Optional<Member> getUserByToken(@Param(value = "token") String token);

    @Query(
            value = "select m.member_id from Member m" +
            " where m.email = :email" +
            " and m.name = :name" +
            " and m.social_login_type = :type"
            ,nativeQuery = true
    )
    Optional<Long> isExistSocialUser(
            @Param(value = "email") String email,
            @Param(value = "name") String name,
            @Param(value = "type") String type
    );
}
