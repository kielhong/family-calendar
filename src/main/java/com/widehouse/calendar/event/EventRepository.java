package com.widehouse.calendar.event;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStartAtBetween(Instant startAtBegin, Instant startAtEnd);
}
