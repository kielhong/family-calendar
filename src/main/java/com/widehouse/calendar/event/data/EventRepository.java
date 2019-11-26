package com.widehouse.calendar.event.data;

import com.widehouse.calendar.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCreatorAndStartAtBetween(User user, ZonedDateTime startAtBegin, ZonedDateTime startAtEnd);

    List<Event> findByStartAtBetween(ZonedDateTime startAtBegin, ZonedDateTime startAtEnd);


}
