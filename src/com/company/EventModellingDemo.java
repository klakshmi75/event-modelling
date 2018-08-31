package com.company;

import com.company.dto.EventDTO;
import com.company.enums.Day;
import com.company.enums.RecurringFrequency;
import com.company.util.DateUtil;

import java.util.*;

public class EventModellingDemo {
    public static void main(String[] args) {
        // Let us find out the planned event on some dates
        String[] dateStrings = {"10/15/2018","10/22/2018", "10/29/2018", "10/16/2018"};

        for(String dateStr: dateStrings) {
            testForDate(DateUtil.getDateFromString(dateStr));
        }
    }
    public static void testForDate(Date date) {
        // Prepare list of event dtos
        // In real time we get this list from database
        // We get an event detail from database if the given date falls between the start date and end date of the event and the week day of the
        // given date is same as the week day of the event.
        // We apply this filters to reduce result set size even though we perform these validations again in java.
        List<EventDTO> eventDTOs = getEventsList();

        Optional<EventDTO> plannedEvent = getPlannedEvent(eventDTOs, date);

        if(plannedEvent.isPresent()) {
            System.out.println("The planned event on " + DateUtil.formatDate(date) + " is " + plannedEvent.get().getDesc());
        } else {
            System.out.println("No event planned on " + DateUtil.formatDate(date));
        }
    }

    public static Optional<EventDTO> getPlannedEvent(List<EventDTO> eventDTOs, Date date) {
        Optional<EventDTO> plannedEvent = eventDTOs.stream().sorted(new Comparator<EventDTO>() {
            /**
             * Arranging the items in decreasing order of priority
             * priority 5 comes first, followed by 4, 3, 2, 1
             *
             * Thereby picking up event with highest priority
             * @param event1
             * @param event2
             * @return
             */
            @Override
            public int compare(EventDTO event1, EventDTO event2) {
                return event2.getRecurringFrequency().getPriority() - event1.getRecurringFrequency().getPriority();
            }
        }).filter(event -> RecurringFrequency.validate(event.getRecurringFrequency(),event.getWeekDay(), event.getStartDate(), event.getEndDate(), date)).
                findFirst();

        return plannedEvent;
    }

    public static List<EventDTO> getEventsList(){
        List<EventDTO> eventDTOs = new ArrayList<>();
        eventDTOs.add(new EventDTO(1, "Yoga", Day.MONDAY, DateUtil.getDateFromString("04/16/2018"), DateUtil.getDateFromString("04/14/2019"), RecurringFrequency.EVERY_WEEK));
        eventDTOs.add(new EventDTO(4, "Aerobics", Day.MONDAY, DateUtil.getDateFromString("05/07/2018"), DateUtil.getDateFromString("04/14/2019"), RecurringFrequency.OTHER_WEEK));
        eventDTOs.add(new EventDTO(5, "Personality Development", Day.MONDAY, DateUtil.getDateFromString("06/25/2018"), null, RecurringFrequency.LAST_WEEK_OF_MONTH));

        return eventDTOs;
    }
}
