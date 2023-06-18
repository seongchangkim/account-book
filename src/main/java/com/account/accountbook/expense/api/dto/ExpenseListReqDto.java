package com.account.accountbook.expense.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseListReqDto {
    private String userId;
    private String date;
    private String lastExpenseId;
}
