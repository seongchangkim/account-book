package com.account.accountbook.expense.repository.dto;

import com.account.accountbook.domain.ExpenseCategory;
import com.account.accountbook.domain.ExpenseStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ExpenseDetailResDto {
    private String content;
    private int expensePrice;
    private String date;
    private ExpenseStatus status;
    private ExpenseCategory category;

    @QueryProjection
    public ExpenseDetailResDto(String content, int expensePrice, String date, ExpenseStatus status, ExpenseCategory category) {
        this.content = content;
        this.expensePrice = expensePrice;
        this.date = date;
        this.status = status;
        this.category = category;
    }
}
