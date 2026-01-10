package com.valuable.valuable._one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class MutableEntity extends BaseEntity {

    /**
     * last update person
     */
    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    /**
     * update date & time
     */
    @LastModifiedDate
    @Column(name = "updated_dt", nullable = false)
    private LocalDateTime updatedDt;

    /**
     * data version for optimistic lock
     */

    @Version
    @Column(name = "VERSION")
    private Integer version;

    public String getUpdatedBy() {
        return updatedBy;
    }

    public MutableEntity setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public LocalDateTime getUpdatedDt() {
        return updatedDt;
    }

    public MutableEntity setUpdatedDt(LocalDateTime updatedDt) {
        this.updatedDt = updatedDt;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public MutableEntity setVersion(Integer version) {
        this.version = version;
        return this;
    }
}
