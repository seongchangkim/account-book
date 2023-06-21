package com.account.accountbook.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonTime {

    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreatedDate
    @Column(updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedBy
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition="TIMESTAMP")
    private Date updatedAt;

    public void setUpdatedAt(Date updatedAt){
        this.updatedAt = updatedAt;
    }
}
