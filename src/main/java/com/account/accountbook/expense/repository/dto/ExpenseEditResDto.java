package com.account.accountbook.expense.repository.dto;

import com.account.accountbook.domain.ExpenseCategory;
import com.account.accountbook.domain.ExpenseStatus;
import lombok.Getter;

import java.util.Date;

@Getter
public class ExpenseEditResDto {

    private Long id;
    private String content;
    private int expensePrice;
    private Date date;
    private ExpenseStatus status;
    private ExpenseCategory category;
    private boolean success;

    public ExpenseEditResDto(Long id, String content, int expensePrice, Date date, ExpenseStatus status, ExpenseCategory category, boolean success) {
        this.id = id;
        this.content = content;
        this.expensePrice = expensePrice;
        this.date = date;
        this.status = status;
        this.category = category;
        this.success = success;
    }
}
