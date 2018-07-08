package com.company.util;

import com.company.enums.Day;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
    private static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static Map<Day, Integer> weekDayMap;

    static {
        weekDayMap = new HashMap<Day, Integer>();
        weekDayMap.put(Day.MONDAY, 1);
        weekDayMap.put(Day.TUESDAY, 2);
        weekDayMap.put(Day.WEDNESDAY, 3);
        weekDayMap.put(Day.THURSDAY, 4);
        weekDayMap.put(Day.FRIDAY, 5);
        weekDayMap.put(Day.SATURDAY, 6);
        weekDayMap.put(Day.SUNDAY, 7);
    }

    public static Date getDateFromString(String dateStr) {
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch(ParseException e) {
            System.out.print("Exception occured while parsing date string: " + dateStr);
        }
        return date;
    }

    public static Date getNextDateForGivenWeekDay(Date date, Day weekDay) {
        LocalDate currentDate = new LocalDate(date);
        LocalDate weekDayDate = currentDate.withDayOfWeek(weekDayMap.get(weekDay));

        if (weekDayDate.isBefore(currentDate)) {
            return weekDayDate.plusWeeks(1).toDate();
        } else {
            return weekDayDate.toDate();
        }
    }

    public static String formatDate(Date date) {
        String formattedDate = "";
        if(date != null) {
            formattedDate = df.format(date);
        }
        return formattedDate;
    }
}
