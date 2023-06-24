package com.account.accountbook.admin.repository;

import com.account.accountbook.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Member, Long>, AdminRepositoryCustom {

}
