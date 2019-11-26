package com.widehouse.calendar.event.data;

import com.widehouse.calendar.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
@Entity
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private final User creator;

    @Column(length = 200)
    private String name;

    @Column(length = 2000)
    private String description;

    private ZonedDateTime startAt;
    private ZonedDateTime endAt;

    public static class EventBuilder {
        /**
         * custom builder.
         * startAt 이 endAt 보다 앞인지 검사
         */
        public Event build() {
            Assert.notNull(creator, "creator should not be null");
            Assert.isTrue(startAt.isBefore(endAt), "event start before endAt");

            return new Event(id, creator, name, description, startAt, endAt);
        }
    }
}
