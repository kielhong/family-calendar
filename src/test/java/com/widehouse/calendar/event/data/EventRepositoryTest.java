package com.widehouse.calendar.event.data;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.calendar.user.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class EventRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private EventRepository repository;

    private User user;

    @BeforeEach
    void setUp() {
        user = entityManager.persist(User.builder().name("user").build());
    }

    @Test
    void create() {
        Event event = Event.builder()
                .creator(user)
                .name("name").description("description")
                .startAt(ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 13, 0, 0), UTC))
                .endAt(ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 14, 0, 0), UTC))
                .build();

        repository.save(event);
    }

    @Test
    void findByCreatorAndStartAtBetweenTest() {
        entityManager.persist(Event.builder()
                .creator(user)
                .name("event1").description("description")
                .startAt(ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 13, 0, 0), UTC))
                .endAt(ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 14, 0, 0), UTC))
                .build());
        entityManager.persist(Event.builder()
                .creator(user)
                .name("event2").description("description")
                .startAt(ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 20, 0, 0), UTC))
                .endAt(ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 23, 0, 0), UTC))
                .build());
        // when
        List<Event> results = repository.findByCreatorAndStartAtBetween(user,
                ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 0, 0, 0), UTC),
                ZonedDateTime.of(LocalDateTime.of(2019, 11, 15, 23, 50, 59), UTC));
        // then
        then(results)
                .extracting("name")
                .contains("event1", "event2");
    }
}
