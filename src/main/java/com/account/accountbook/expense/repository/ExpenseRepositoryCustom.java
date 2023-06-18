package com.account.accountbook.expense.repository;

import com.account.accountbook.expense.repository.dto.ExpenseDetailResDto;
import com.account.accountbook.expense.repository.dto.ExpenseListResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Date;

public interface ExpenseRepositoryCustom {

    public Slice<ExpenseListResDto> getExpenseListByDate(Long lastExpenseId, Date date, Long userId, Pageable pageable);

    public ExpenseDetailResDto getExpenseById(Long id);
}
