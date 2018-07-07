package com.company.enums;

/**
 * Created by lakshmik on 4/7/18.
 */
import java.time.LocalDate;
import java.util.Date;
import org.joda.time.DateTime;

    public enum Day {

        MONDAY(1,"M"),
        TUESDAY(2,"Tu"),
        WEDNESDAY(3,"W"),
        THURSDAY(4,"Th"),
        FRIDAY(5,"F"),
        SATURDAY(6,"Sa"),
        SUNDAY(7,"Su");

        private int dayIndex;
        private String shortName;

        private Day(int day, String shortName){
            this.dayIndex=day; this.shortName=shortName;
        }

        public int getDayIndex() {
            return dayIndex;
        }

        public String getShortName() {
            return shortName;
        }

        public static Day getDayFromString(String dayString){
            for(Day day:Day.values()){
                if(day.toString().equals(dayString)){
                    return day;
                }
            }
            return null;
        }

        public static Day getDayFromDayIndex(int dayIndex){
            for(Day day:Day.values()){
                if(day.getDayIndex()==dayIndex){
                    return day;
                }
            }
            return null;
        }

        public static Day getDayFromDate(Date previewDate){
            return Day.getDayFromDayIndex((new DateTime(previewDate).getDayOfWeek()));
        }

        public static Day getDayFromLocalDate(LocalDate date){
            return Day.getDayFromDayIndex(date.getDayOfWeek().getValue());
        }

        public int getWeekDayIndexForCron() {
            return dayIndex == 7 ? 1 : dayIndex + 1;
        }

        public static Day getDayFromShortName(String shortName){
            for(Day day:Day.values()){
                if(day.getShortName().equals(shortName)){
                    return day;
                }
            }
            return null;
        }

}
