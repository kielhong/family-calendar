package com.widehouse.calendar.event.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.widehouse.calendar.event.Event;
import com.widehouse.calendar.event.EventRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    private EventService service;
    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        service = new EventService(eventRepository);
    }

    @Test
    void findByDay_GivenLocalDate_ThenListWithinLocalDate() {
        // given
        Event event1 = Event.builder()
                .name("event1").description("event1")
                .startAt(ZonedDateTime.of(2019, 11, 1, 9, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .endAt(ZonedDateTime.of(2019, 11, 1, 10, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .build();
        Event event2 = Event.builder()
                .name("event2").description("event2")
                .startAt(ZonedDateTime.of(2019, 11, 1, 13, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .endAt(ZonedDateTime.of(2019, 11, 1, 14, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .build();
        given(eventRepository.findByStartAtBetween(any(Instant.class), any(Instant.class)))
                .willReturn(Arrays.asList(event1, event2));
        // when
        List<Event> results = service.findByDate(LocalDate.of(2019, 11, 1), ZoneOffset.UTC);
        // then
        then(results)
                .containsOnly(event1, event2);
    }

    @Test
    void findByMonth_GivenYearMonth_ThenListWithinLocalMonth() {
        Event event1 = Event.builder()
                .name("event1").description("event1")
                .startAt(ZonedDateTime.of(2019, 11, 1, 9, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .endAt(ZonedDateTime.of(2019, 11, 1, 10, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .build();
        Event event2 = Event.builder()
                .name("event2").description("event2")
                .startAt(ZonedDateTime.of(2019, 11, 15, 13, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .endAt(ZonedDateTime.of(2019, 11, 15, 14, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .build();
        Event event3 = Event.builder()
                .name("event3").description("event3")
                .startAt(ZonedDateTime.of(2019, 11, 30, 13, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .endAt(ZonedDateTime.of(2019, 11, 30, 14, 0, 0, 0, ZoneOffset.UTC).toInstant())
                .build();
        given(eventRepository.findByStartAtBetween(any(Instant.class), any(Instant.class)))
                .willReturn(Arrays.asList(event1, event2, event3));
        // when
        List<Event> events = service.findByMonth(YearMonth.of(2019, 11), ZoneOffset.UTC);
        // then
        then(events)
                .containsOnly(event1, event2, event3);

    }
}
