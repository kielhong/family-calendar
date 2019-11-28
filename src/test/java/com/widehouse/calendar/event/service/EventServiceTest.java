package com.widehouse.calendar.event.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.calendar.event.data.Event;
import com.widehouse.calendar.event.data.EventRepository;
import com.widehouse.calendar.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private User user;
    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    void setUp() {
        service = new EventService(eventRepository);

        user = User.builder().name("user").build();

        event1 = Event.builder()
                .creator(user)
                .name("event1").description("event1")
                .startAt(ZonedDateTime.of(2019, 11, 1, 9, 0, 0, 0, ZoneOffset.UTC))
                .endAt(ZonedDateTime.of(2019, 11, 1, 10, 0, 0, 0, ZoneOffset.UTC))
                .build();
        event2 = Event.builder()
                .creator(user)
                .name("event2").description("event2")
                .startAt(ZonedDateTime.of(2019, 11, 15, 13, 0, 0, 0, ZoneOffset.UTC))
                .endAt(ZonedDateTime.of(2019, 11, 15, 14, 0, 0, 0, ZoneOffset.UTC))
                .build();
        event3 = Event.builder()
                .creator(user)
                .name("event3").description("event3")
                .startAt(ZonedDateTime.of(2019, 11, 30, 13, 0, 0, 0, ZoneOffset.UTC))
                .endAt(ZonedDateTime.of(2019, 11, 30, 14, 0, 0, 0, ZoneOffset.UTC))
                .build();
    }

    @Test
    void findByDay_GivenUserAndLocalDate_ThenListWithinLocalDateAndStartAtOrderd() {
        // given
        given(eventRepository.findByCreatorAndStartAtBetween(eq(user), any(ZonedDateTime.class),
                any(ZonedDateTime.class)))
                .willReturn(Arrays.asList(event2, event1));
        // when
        List<Event> results = service.findByDate(user, LocalDate.of(2019, 11, 1), ZoneOffset.UTC);
        // then
        then(results)
                .containsExactly(event1, event2);
    }

    @Test
    void findByMonth_GivenUserAndYearMonth_ThenListWithinLocalMonthSortedStartAt() {
        given(eventRepository.findByCreatorAndStartAtBetween(eq(user),
                any(ZonedDateTime.class), any(ZonedDateTime.class)))
                .willReturn(Arrays.asList(event2, event1, event3));
        // when
        List<Event> events = service.findByMonth(user, YearMonth.of(2019, 11), ZoneOffset.UTC);
        // then
        then(events)
                .containsExactly(event1, event2, event3);
    }

    @Test
    void createEvent_ThenReturnEvent() {
        // given
        given(eventRepository.save(any(Event.class)))
                .willReturn(event1);
        // when
        Event result = service.createEvent(user, "name", "desc", LocalDateTime.of(2019, 11, 5, 13, 0, 0),
                LocalDateTime.of(2019, 11, 5, 14, 0, 0), ZoneOffset.UTC);
        // then
        then(result).isEqualTo(event1);
        verify(eventRepository).save(any(Event.class));
    }
}
