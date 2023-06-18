package com.account.accountbook.expense.api.form;

import com.account.accountbook.domain.ExpenseCategory;
import com.account.accountbook.domain.ExpenseStatus;
import lombok.Data;

@Data
public class ExpenseForm {

    private Long userId;
    private String content;
    private String expense;
    private String date;
    private ExpenseStatus status;
    private ExpenseCategory category;

}
