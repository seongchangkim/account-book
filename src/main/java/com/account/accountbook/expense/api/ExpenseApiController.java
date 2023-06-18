package com.account.accountbook.expense.api;

import com.account.accountbook.expense.api.dto.ExpenseListReqDto;
import com.account.accountbook.expense.api.form.ExpenseForm;
import com.account.accountbook.expense.repository.dto.ExpenseDetailResDto;
import com.account.accountbook.expense.repository.dto.ExpenseListResDto;
import com.account.accountbook.expense.service.ExpenseService;
import com.account.accountbook.expense.repository.dto.ExpenseEditResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExpenseApiController {
    private final ExpenseService service;

    // 지출 또는 수입 항목 생성
    @PostMapping("/api/expense")
    public ResponseEntity<Map<String, Boolean>> addExpense(@RequestBody ExpenseForm form) throws ParseException {
        Long addedExpenseId = service.addExpense(form);

        Map<String, Boolean> result = new HashMap<>();
        result.put("success", true);

        return ResponseEntity.created(URI.create("/expense/" + addedExpenseId))
                .header("Content-type", "application/json;charset=UTF-8").body(result);
    }

    // 날짜별 지출 또는 수입 항목 목록 불러오기
    @PostMapping(value = "/api/expense/list", produces = "application/json;charset=UTF-8")
    public Slice<ExpenseListResDto> getExpenseListByDate(@RequestBody ExpenseListReqDto req, Pageable pageable) throws ParseException {
        return service.getExpenseListByDate(req.getLastExpenseId(), req.getDate(), req.getUserId(), pageable);
    }

    @GetMapping(value = "/api/expense/{id}", produces = "application/json;charset=UTF-8")
    public ExpenseDetailResDto getExpenseById(@PathVariable("id") Long id){
        return service.getExpenseById(id);
    }

    @PatchMapping(value = "/api/expense/{id}", produces = "application/json;charset=UTF-8")
    public ExpenseEditResDto editExpense(@PathVariable("id") Long id, @RequestBody ExpenseForm form) throws ParseException {
        return service.editExpense(id, form);
    }

    @DeleteMapping(value = "/api/expense/{id}")
    public ResponseEntity<Map<String, Boolean>> editExpense(@PathVariable("id") Long id) throws ParseException {
        Boolean success = service.deleteExpense(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("success", success);

        return ResponseEntity.ok()
                .header("Content-type", "application/json;charset=UTF-8").body(result);
    }
}
