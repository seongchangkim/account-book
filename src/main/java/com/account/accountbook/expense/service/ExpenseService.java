package com.account.accountbook.expense.service;

import com.account.accountbook.domain.Expense;
import com.account.accountbook.domain.Member;
import com.account.accountbook.expense.api.form.ExpenseForm;
import com.account.accountbook.expense.repository.ExpenseRepository;
import com.account.accountbook.expense.repository.dto.ExpenseDetailResDto;
import com.account.accountbook.expense.repository.dto.ExpenseListResDto;
import com.account.accountbook.expense.repository.dto.ExpenseEditResDto;
import com.account.accountbook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public Long addExpense(ExpenseForm form) throws ParseException {
        Member findMember = userRepository.findById(form.getUserId()).orElse(null);

        Date setDate = parseFormatDate(form.getDate());

        Expense expense = new Expense();
        expense.expenseForm(form.getContent(), Integer.parseInt(form.getExpense()), setDate, form.getStatus(), form.getCategory());
        expense.setMember(findMember);

        Expense savedExpense = expenseRepository.save(expense);
        return savedExpense.getId();
    }

    public Slice<ExpenseListResDto> getExpenseListByDate(String lastExpenseId, String date, String userId, Pageable pageable) throws ParseException {
        Date setDate = parseFormatDate(date);

        return expenseRepository.getExpenseListByDate(lastExpenseId != null ? Long.parseLong(lastExpenseId) : null ,setDate, Long.parseLong(userId), pageable);
    }

    public ExpenseDetailResDto getExpenseById(Long id){
        return expenseRepository.getExpenseById(id);
    }

    public ExpenseEditResDto editExpense(Long id, ExpenseForm form) throws ParseException {
        Expense findExpense = expenseRepository.findById(id).orElse(null);
        Date setDate = parseFormatDate(form.getDate());
        findExpense.expenseForm(form.getContent(), Integer.parseInt(form.getExpense()), setDate, form.getStatus(), form.getCategory());

        return new ExpenseEditResDto(id, form.getContent(), Integer.parseInt(form.getExpense()), setDate, form.getStatus(), form.getCategory(), true);
    }

    public Boolean deleteExpense(Long id){
        Expense findExpense = expenseRepository.findById(id).orElse(null);

        expenseRepository.delete(findExpense);

        return true;
    }

    // String -> Date 타입으로 변환하면서 날짜 형식으로 변환하는 기능(공통 메소드)
    private static Date parseFormatDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(date);
    }
}
