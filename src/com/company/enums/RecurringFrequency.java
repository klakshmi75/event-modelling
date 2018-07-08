package com.company.enums;

import java.text.ParseException;
import java.util.Date;

import com.company.util.DateUtil;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import play.libs.Time;

/**
 * Highest Priority indicated by Highest int value of the variable `priority`
 */
public enum RecurringFrequency {
    EVERY_WEEK(1),
    OTHER_WEEK(2),
    FIRST_WEEK_OF_MONTH(3),
    SECOND_WEEK_OF_MONTH(3),
    THIRD_WEEK_OF_MONTH(3),
    FOURTH_WEEK_OF_MONTH(3),
    LAST_WEEK_OF_MONTH(4),
    OCCURS_ONCE(5);

    private int priority;

    private RecurringFrequency(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static boolean validate(RecurringFrequency recurringFrequency, Day weekDay, Date eventStartDate, Date eventEndDate, Date givenDate) {
        boolean result = false;
        LocalDate startDate = eventStartDate == null ? null : new LocalDate(eventStartDate);
        LocalDate endDate = eventEndDate == null ? null : new LocalDate(eventEndDate);
        LocalDate date = givenDate == null ? null : new LocalDate(givenDate);

        if(validateBasicCriteria(weekDay, startDate, endDate, date)) {
            return false;
        }
        switch (recurringFrequency) {
            case OCCURS_ONCE:
                result = validateOccursOnce(startDate, date);
                break;
            case LAST_WEEK_OF_MONTH:
            case FIRST_WEEK_OF_MONTH:
            case SECOND_WEEK_OF_MONTH:
            case THIRD_WEEK_OF_MONTH:
            case FOURTH_WEEK_OF_MONTH:
            case EVERY_WEEK:
                result = validateSpecificWeekOfMonth(recurringFrequency, weekDay, date);
                break;
            case OTHER_WEEK:
                result = validateOtherWeek(recurringFrequency, weekDay, startDate, date);
                break;
            default:
                break;
        }

        return result;
    }

    private static boolean validateBasicCriteria(Day weekDay, LocalDate startDate, LocalDate endDate, LocalDate date) {
        if(weekDay == null || startDate == null || date == null) {
            return false;
        }
        if(!Day.getDayFromDate(date.toDate()).equals(date)) {
            return false;
        }
        if(endDate == null) {
            if(date.isBefore(startDate)) {
                return false;
            }
        } else {
            if(date.isBefore(startDate) || date.isAfter(endDate)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateSpecificWeekOfMonth(RecurringFrequency recurringFrequency, Day weekDay,
                                                       LocalDate date) {
        StringBuilder cronExpression = new StringBuilder("* * * ? * ");
        switch (recurringFrequency) {
            case FIRST_WEEK_OF_MONTH:
                cronExpression.append(weekDay.getWeekDayIndexForCron() + "#1");
                break;
            case SECOND_WEEK_OF_MONTH:
                cronExpression.append(weekDay.getWeekDayIndexForCron() + "#2");
                break;
            case THIRD_WEEK_OF_MONTH:
                cronExpression.append(weekDay.getWeekDayIndexForCron() + "#3");
                break;
            case FOURTH_WEEK_OF_MONTH:
                cronExpression.append(weekDay.getWeekDayIndexForCron() + "#4");
                break;
            case LAST_WEEK_OF_MONTH:
                cronExpression.append(weekDay.getWeekDayIndexForCron() + "L");
                break;
            case EVERY_WEEK:
                cronExpression.append(weekDay.getWeekDayIndexForCron());
                break;
            default:
                break;
        }
        try {
            Time.CronExpression exp = new Time.CronExpression(cronExpression.toString());
            return exp.isSatisfiedBy(date.toDate());
        } catch (ParseException e) {
            System.out.print("Some error in building cron expression" + cronExpression.toString());
        }
        return false;
    }

    private static boolean validateOtherWeek(RecurringFrequency recurringFrequency, Day day, LocalDate startDate, LocalDate date) {
        Date startDateAdjustedToGivenWeekDay = DateUtil.getNextDateForGivenWeekDay(startDate.toDate(), day);
        return Days.daysBetween(startDate, date).getDays() % 14 == 0;
    }

    private static boolean validateOccursOnce(LocalDate startDate, LocalDate date) {
        return startDate.isEqual(date);
    }
}
