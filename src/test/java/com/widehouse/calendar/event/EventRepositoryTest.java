package com.widehouse.calendar.event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class EventRepositoryTest {
    @Autowired
    private EventRepository repository;

    @Test
    void create() {
        Event event = Event.builder()
                .name("name").description("description")
                .startAt(ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 13, 0, 0), ZoneOffset.UTC))
                .endAt(ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 14, 0, 0), ZoneOffset.UTC))
                .build();

        repository.save(event);
    }
}
