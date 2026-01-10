package com.valuable.valuable._one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    /**
     * created person
     */
    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    /**
     * create date & time
     */
    @CreatedDate
    @Column(name = "created_dt", updatable = false, nullable = false)
    private LocalDateTime createdDt;

    public String getCreatedBy() {
        return createdBy;
    }

    public BaseEntity setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public BaseEntity setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
        return this;
    }
}
