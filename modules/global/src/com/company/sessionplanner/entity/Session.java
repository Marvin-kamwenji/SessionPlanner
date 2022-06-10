package com.company.sessionplanner.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Table(name = "SESSIONPLANNER_SESSION")
@Entity(name = "sessionplanner_Session")
@NamePattern("%s|description")
public class Session extends StandardEntity {
    private static final long serialVersionUID = 7497709835929871175L;

    @NotNull
    @Column(name = "TOPIC", nullable = false)
    private String topic;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SPEAKER_ID")
    private Session speaker;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Session getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Session speaker) {
        this.speaker = speaker;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @PrePersist
    @PreUpdate
    public void updateEndDate() {
     endDate = calculateEndDate(startDate);
    }

    public static Date calculateEndDate(Date startDate){
        return Date.from(startDate.toInstant().plus(1, ChronoUnit.HOURS));
    }
}