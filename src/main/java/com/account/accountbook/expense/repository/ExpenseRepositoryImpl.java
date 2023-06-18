package com.account.accountbook.expense.repository;

import com.account.accountbook.expense.repository.dto.ExpenseDetailResDto;
import com.account.accountbook.expense.repository.dto.ExpenseListResDto;
import com.account.accountbook.expense.repository.dto.QExpenseDetailResDto;
import com.account.accountbook.expense.repository.dto.QExpenseListResDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static com.account.accountbook.domain.QExpense.*;

public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ExpenseRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<ExpenseListResDto> getExpenseListByDate(Long lastExpenseId, Date date, Long userId, Pageable pageable) {
        List<ExpenseListResDto> result = queryFactory.select(
            new QExpenseListResDto(
                    expense.id,
                    expense.content,
                    expense.expensePrice,
                    expense.status,
                    expense.category)
        ).from(expense)
        .where(ltExpenseId(lastExpenseId), expense.date.eq(date), expense.member.id.eq(userId))
        .orderBy(expense.id.desc())
        .limit(pageable.getPageSize()+1).fetch();

        return checkLastPage(pageable, result);
    }

    @Override
    public ExpenseDetailResDto getExpenseById(Long id) {
        return queryFactory.select(
            new QExpenseDetailResDto(
                expense.content,
                expense.expensePrice,
                dateFormat(expense.date),
                expense.status,
                expense.category
            )
        ).from(expense).where(expense.id.eq(id)).fetchOne();
    }

    // DB 전화번호 포멧 지정(yyyy-mm-dd)
    private StringExpression dateFormat(DateTimePath<Date> date) {
        return date.year().stringValue()
                .concat("-")
                .concat(date.month().stringValue())
                .concat("-")
                .concat(date.dayOfMonth().stringValue());
    }

    // SQL no-offset
    private BooleanExpression ltExpenseId(Long expenseId){
        if(expenseId == null){
            return null;
        }else{
            return expense.id.lt(expenseId);
        }
    }

    //
    private Slice<ExpenseListResDto> checkLastPage(Pageable pageable, List<ExpenseListResDto> result){
        boolean hasNext = false;

        if(result.size() > pageable.getPageSize()){
            hasNext = true;
            result.remove(pageable.getPageSize());
        }

        return new SliceImpl(result, pageable, hasNext);
    }
}
