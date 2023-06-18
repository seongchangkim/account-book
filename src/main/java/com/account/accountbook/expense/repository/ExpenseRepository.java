package com.account.accountbook.expense.repository;

import com.account.accountbook.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryCustom{
}
