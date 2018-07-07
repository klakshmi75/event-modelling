package com.company.dto;

import com.company.enums.Day;
import com.company.enums.RecurringFrequency;

import java.util.Date;

public class EventDTO {
    private Integer id;
    private String desc;
    private Day weekDay;
    private Date startDate;
    private Date endDate;
    private RecurringFrequency recurringFrequency;

    public EventDTO(Integer id, String desc, Day weekDay, Date startDate, Date endDate, RecurringFrequency recurringFrequency) {
        this.id = id;
        this.desc = desc;
        this.weekDay = weekDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurringFrequency = recurringFrequency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Day getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Day weekDay) {
        this.weekDay = weekDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public RecurringFrequency getRecurringFrequency() {
        return recurringFrequency;
    }

    public void setRecurringFrequency(RecurringFrequency recurringFrequency) {
        this.recurringFrequency = recurringFrequency;
    }
}
