package com.account.accountbook.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id", updatable = false)
    private Long id;

    private String content;
    private int expensePrice;

    @Column(name = "expense_date")
    private Date date;

    @Column(name = "expense_status")
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'EXPENSE'")
    private ExpenseStatus status;

    @Column(name = "expense_category")
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'OTHER'")
    private ExpenseCategory category;

    @Embedded
    private CommonTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 비용 또는 수입 항목 추가 및 수정 로직
    public void expenseForm(String content, int expensePrice, Date date, ExpenseStatus status, ExpenseCategory category) {
        this.content = content;
        this.expensePrice = expensePrice;
        this.date = date;
        this.status = status;
        this.category = category;
    }

    // 비용 또는 수입 항목 추가 로직 2
    public void setMember(Member member){
        this.member = member;
    }
}
