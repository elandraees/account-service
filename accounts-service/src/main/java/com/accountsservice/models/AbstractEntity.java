package com.accountsservice.models;


import com.accountsservice.utils.AbstractResult;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    enum Status {

        ACTIVE, TERMINATED;

        public static int getStatusId(Status status) {
            return status.ordinal();
        }
    }

    @Column(name = "id")
    private long id;

    @Column(name = "create_date")
    @Getter
    @Setter
    private Date createDate;

    @Column(name = "last_update_date")
    @Getter
    @Setter
    private Date lastUpdateDate;

    @Column(name = "status_id")
    @Getter
    @Setter
    private int statusId;

    public AbstractEntity() {
        this.createDate = new Date();
        this.lastUpdateDate = new Date();
        this.statusId = Status.ACTIVE.ordinal();
    }

    public AbstractEntity(Date createDate, Date lastUpdateDate) {
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.statusId = Status.ACTIVE.ordinal();
    }

    public abstract AbstractResult validate(boolean forInsert);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
