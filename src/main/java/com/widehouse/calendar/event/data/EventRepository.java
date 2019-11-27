package com.widehouse.calendar.event.data;

import com.widehouse.calendar.user.User;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCreatorAndStartAtBetween(User user, ZonedDateTime startAtBegin, ZonedDateTime startAtEnd);
}
