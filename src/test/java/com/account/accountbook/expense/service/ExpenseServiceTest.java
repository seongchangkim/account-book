package com.account.accountbook.expense.service;

import com.account.accountbook.domain.Expense;
import com.account.accountbook.domain.ExpenseCategory;
import com.account.accountbook.domain.ExpenseStatus;
import com.account.accountbook.domain.Member;
import com.account.accountbook.expense.api.form.ExpenseForm;
import com.account.accountbook.expense.repository.ExpenseRepository;
import com.account.accountbook.expense.repository.dto.ExpenseDetailResDto;
import com.account.accountbook.expense.repository.dto.ExpenseListResDto;
import com.account.accountbook.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ExpenseServiceTest {

    @Autowired private ExpenseService expenseService;
    @Autowired private UserService userService;
    @Autowired private PasswordEncoder encoder;
    @Autowired private ExpenseRepository expenseRepository;

    @Test
    public void 수입_항목_추가() throws Exception{
        //given
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Long registeredId = userService.register(member);

        ExpenseForm form = new ExpenseForm();
        form.setUserId(registeredId);
        form.setContent("저축");
        form.setExpense("200000");
        form.setDate("2023-06-16");
        form.setCategory(ExpenseCategory.SAVING_EXPENSE);
        form.setStatus(ExpenseStatus.INCOME);
        Long addedExpenseId = expenseService.addExpense(form);

        //when
        Expense findExpense = expenseRepository.findById(addedExpenseId).get();

        //then
        assertThat(findExpense.getExpensePrice()).isEqualTo(Integer.parseInt(form.getExpense()));
        assertThat(findExpense.getContent()).isEqualTo(form.getContent());
        assertThat(findExpense.getDate()).isEqualTo(form.getDate());
        assertThat(findExpense.getStatus()).isEqualTo(form.getStatus());
        assertThat(findExpense.getCategory()).isEqualTo(form.getCategory());
    }

    @Test
    public void 날짜별_수입_지출_항목_목록() throws Exception{
        //given
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Long registeredId = userService.register(member);

        ExpenseForm form1 = new ExpenseForm();
        form1.setUserId(registeredId);
        form1.setContent("저축");
        form1.setExpense("200000");
        form1.setDate("2023-06-16");
        form1.setCategory(ExpenseCategory.SAVING_EXPENSE);
        form1.setStatus(ExpenseStatus.INCOME);
        Long addedExpenseId1 = expenseService.addExpense(form1);

        ExpenseForm form2 = new ExpenseForm();
        form2.setUserId(registeredId);
        form2.setContent("용돈");
        form2.setExpense("200000");
        form2.setDate("2023-06-16");
        form2.setCategory(ExpenseCategory.INCOME_EXPENSE);
        form2.setStatus(ExpenseStatus.INCOME);
        Long addedExpenseId2 = expenseService.addExpense(form2);

        ExpenseForm form3 = new ExpenseForm();
        form3.setUserId(registeredId);
        form3.setContent("투자");
        form3.setExpense("130000");
        form3.setDate("2023-06-16");
        form3.setCategory(ExpenseCategory.INVEST_EXPENSE);
        form3.setStatus(ExpenseStatus.INCOME);
        Long addedExpenseId3 = expenseService.addExpense(form3);

        ExpenseForm form4 = new ExpenseForm();
        form4.setUserId(registeredId);
        form4.setContent("청약통장저금");
        form4.setExpense("50000");
        form4.setDate("2023-06-16");
        form4.setCategory(ExpenseCategory.SAVING_EXPENSE);
        form4.setStatus(ExpenseStatus.INCOME);
        Long addedExpenseId4 = expenseService.addExpense(form4);

        PageRequest pageReq = PageRequest.ofSize(4);

        //when
        Slice<ExpenseListResDto> result = expenseService.getExpenseListByDate("5","2023-06-16", registeredId.toString(), pageReq);

        result.toList().forEach((res) -> System.out.println("res = " + res.getContent()));
        //then
        assertThat(result.isLast()).isEqualTo(true);
    }

    @Test
    public void 수입_지출_항목_상세보기() throws Exception{
        //given
        Member member = new Member();
        member.registerMember("test@test.com", encoder.encode("test1234"), "010-1111-1111", "test");
        Long registeredId = userService.register(member);

        ExpenseForm form = new ExpenseForm();
        form.setUserId(registeredId);
        form.setContent("저축");
        form.setExpense("200000");
        form.setDate("2023-06-16");
        form.setCategory(ExpenseCategory.SAVING_EXPENSE);
        form.setStatus(ExpenseStatus.INCOME);
        Long addedExpenseId = expenseService.addExpense(form);

        //when
        ExpenseDetailResDto res = expenseService.getExpenseById(addedExpenseId);

        //then
        assertThat(res.getCategory()).isEqualTo(form.getCategory());
        assertThat(res.getExpensePrice()).isEqualTo(Integer.parseInt(form.getExpense()));
//        assertThat(res.getDate()).isEqualTo(form.getDate());
        assertThat(res.getContent()).isEqualTo(form.getContent());
        assertThat(res.getStatus()).isEqualTo(form.getStatus());
    }
}