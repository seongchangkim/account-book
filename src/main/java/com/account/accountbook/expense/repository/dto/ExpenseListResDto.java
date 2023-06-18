package com.account.accountbook.expense.repository.dto;


import com.account.accountbook.domain.ExpenseCategory;
import com.account.accountbook.domain.ExpenseStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ExpenseListResDto {

    private Long id;
    private String content;
    private int expensePrice;
    private ExpenseStatus status;
    private ExpenseCategory category;

    @QueryProjection
    public ExpenseListResDto(Long id, String content, int expensePrice, ExpenseStatus status, ExpenseCategory category) {
        this.id = id;
        this.content = content;
        this.expensePrice = expensePrice;
        this.status = status;
        this.category = category;
    }
}
