package com.company.enums;

import java.text.ParseException;
import java.util.Date;

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

    public static boolean validate(RecurringFrequency recurringFrequency, Date eventStartDate, Date date) {
        boolean result = false;

        switch (recurringFrequency) {
            case OCCURS_ONCE:
                result = validateOccursOnce(eventStartDate, date);
                break;
            case LAST_WEEK_OF_MONTH:
            case FIRST_WEEK_OF_MONTH:
            case SECOND_WEEK_OF_MONTH:
            case THIRD_WEEK_OF_MONTH:
            case FOURTH_WEEK_OF_MONTH:
            case EVERY_WEEK:
                result = validateSpecificWeekOfMonth(recurringFrequency, eventStartDate, date);
                break;
            case OTHER_WEEK:
                result = validateOtherWeek(recurringFrequency, eventStartDate, date);
                break;
            default:
                break;

        }

        return result;
    }

    private static boolean validateOccursOnce(Date eventStartDate, Date date) {
        LocalDate jodaDate1 = new LocalDate(eventStartDate);
        LocalDate jodaDate2 = new LocalDate(date);
        return jodaDate1.isEqual(jodaDate2);
    }

    private static boolean validateSpecificWeekOfMonth(RecurringFrequency recurringFrequency, Date eventStartDate,
                                                       Date date) {
        StringBuilder cronExpression = new StringBuilder("* * * ? * ");
        Day weekdayOfEventStartDate = Day.getDayFromDate(eventStartDate);
        switch (recurringFrequency) {
            case FIRST_WEEK_OF_MONTH:
                cronExpression.append(weekdayOfEventStartDate.getWeekDayIndexForCron() + "#1");
                break;
            case SECOND_WEEK_OF_MONTH:
                cronExpression.append(weekdayOfEventStartDate.getWeekDayIndexForCron() + "#2");
                break;
            case THIRD_WEEK_OF_MONTH:
                cronExpression.append(weekdayOfEventStartDate.getWeekDayIndexForCron() + "#3");
                break;
            case FOURTH_WEEK_OF_MONTH:
                cronExpression.append(weekdayOfEventStartDate.getWeekDayIndexForCron() + "#4");
                break;
            case LAST_WEEK_OF_MONTH:
                cronExpression.append(weekdayOfEventStartDate.getWeekDayIndexForCron() + "L");
                break;
            case EVERY_WEEK:
                cronExpression.append(weekdayOfEventStartDate.getWeekDayIndexForCron());

                break;
            default:
                break;
        }
        try {
            Time.CronExpression exp = new Time.CronExpression(cronExpression.toString());
            return exp.isSatisfiedBy(date);
        } catch (ParseException e) {
            System.out.print("Some error in building cron expression" + cronExpression.toString());
        }
        return false;
    }

    // This method assumes that the eventStartDate is always the same weekday as the weekday of date to be validated.
    private static boolean validateOtherWeek(RecurringFrequency recurringFrequency, Date eventStartDate, Date date) {
        LocalDate jodaDate1 = new LocalDate(eventStartDate);
        LocalDate jodaDate2 = new LocalDate(date);
        return Days.daysBetween(jodaDate1, jodaDate2).getDays() % 14 == 0;
    }
}
