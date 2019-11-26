package com.widehouse.calendar.event.service;

import com.widehouse.calendar.event.data.Event;
import com.widehouse.calendar.event.data.EventRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import com.widehouse.calendar.user.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    /**
     * 주어진 일자 기준으로 startAt 이 해당 일자에 속하는 Event 목록을 반환.
     * @param localDate 일자
     * @param zoneOffset ZoneOffset
     * @return Event 목록. 없으면 empty list
     */
    public List<Event> findByDate(LocalDate localDate, ZoneOffset zoneOffset) {
        ZonedDateTime startAtBegin =  ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, zoneOffset);
        ZonedDateTime startAtEnd =  ZonedDateTime.of(localDate, LocalTime.MAX, zoneOffset);

        return eventRepository.findByStartAtBetween(startAtBegin, startAtEnd);
    }

    /**
     * 주어진 달의 startAt이 첫날 MIDNIGHT 부터 마지막 날 자정까지 속하는 Event 목록을 반환.
     * @param yearMonth 년-월
     * @param zoneOffset ZoneOffset
     * @return Event 목록. 없으면 empty list
     */
    public List<Event> findByMonth(YearMonth yearMonth, ZoneOffset zoneOffset) {
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());

        ZonedDateTime startAtBegin =  ZonedDateTime.of(firstDay, LocalTime.MIDNIGHT, zoneOffset);
        ZonedDateTime startAtEnd =  ZonedDateTime.of(lastDay, LocalTime.MAX, zoneOffset);

        return eventRepository.findByStartAtBetween(startAtBegin, startAtEnd);
    }

    public Event createEvent(User user, String name, String description, LocalDateTime startAt, LocalDateTime endAt,
                             ZoneOffset zoneOffset) {
        Event event = Event.builder()
                .creator(user)
                .name(name).description(description)
                .startAt(ZonedDateTime.of(startAt, zoneOffset))
                .endAt(ZonedDateTime.of(endAt, zoneOffset))
                .build();

        return eventRepository.save(event);
    }
}
