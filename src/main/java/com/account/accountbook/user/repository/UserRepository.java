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
}
