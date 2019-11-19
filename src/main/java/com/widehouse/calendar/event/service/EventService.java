package com.widehouse.calendar.event.service;

import com.widehouse.calendar.event.Event;
import com.widehouse.calendar.event.EventRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    /**
     * 주어진 일자 기준으로 startAt 이 해당 일자에 속하는 Event 목록을 반환.
     * @param localDate 일자
     * @param zoneId ZoneId
     * @return Event 목록
     */
    public List<Event> findByDate(LocalDate localDate, ZoneId zoneId) {
        Instant startAtBegin =  ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, zoneId).toInstant();
        Instant startAtEnd =  ZonedDateTime.of(localDate.plusDays(1), LocalTime.MIDNIGHT, zoneId).toInstant();

        return eventRepository.findByStartAtBetween(startAtBegin, startAtEnd);
    }
}
