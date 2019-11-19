package com.widehouse.calendar.event;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Entity
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Instant startAt;
    private Instant endAt;

    public static class EventBuilder {
        /**
         * custom builder.
         * startAt 이 endAt 보다 앞인지 검사
         */
        public Event build() {
            if (startAt.isAfter(endAt)) {
                throw new IllegalArgumentException("startAt is behind endAt");
            }

            return new Event(id, name, description, startAt, endAt);
        }
    }
}
