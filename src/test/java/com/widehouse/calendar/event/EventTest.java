package com.widehouse.calendar.event;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.widehouse.calendar.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().name("user").build();
    }

    @Test
    void buildTest() {
        // given
        ZonedDateTime startAt = ZonedDateTime.of(2019, 11, 1, 13, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime endAt = ZonedDateTime.of(2019, 11, 1, 14, 0, 0, 0, ZoneOffset.UTC);
        // when
        Event event = Event.builder()
                        .creator(user)
                        .name("event name")
                        .description("event description")
                        .startAt(startAt)
                        .endAt(endAt)
                        .build();
        // then
        then(event)
                .hasFieldOrPropertyWithValue("creator", user)
                .hasFieldOrPropertyWithValue("name", "event name")
                .hasFieldOrPropertyWithValue("description", "event description")
                .hasFieldOrPropertyWithValue("startAt", startAt)
                .hasFieldOrPropertyWithValue("endAt", endAt);
    }

    @Test
    void builder_NonNull_ThrowException() {
        Event.EventBuilder eventBuilder = Event.builder()
                .name("event name")
                .description("event description")
                .startAt(ZonedDateTime.of(2019, 11, 1, 13, 0, 0, 0, ZoneOffset.UTC))
                .endAt(ZonedDateTime.of(2019, 11, 1, 15, 0, 0, 0, ZoneOffset.UTC));
        // when
        thenThrownBy(() -> eventBuilder.build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void startAtIsBehindEndAt_ThrowEventDateException() {
        // when
        Event.EventBuilder eventBuilder = Event.builder()
                .name("event name")
                .description("event description")
                .startAt(ZonedDateTime.of(2019, 11, 1, 15, 0, 0, 0, ZoneOffset.UTC))
                .endAt(ZonedDateTime.of(2019, 11, 1, 13, 0, 0, 0, ZoneOffset.UTC));

        thenThrownBy(eventBuilder::build)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
