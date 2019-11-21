package com.widehouse.calendar.event;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Entity
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
            if (startAt.isAfter(endAt)) {
                throw new IllegalArgumentException("startAt is behind endAt");
            }

            return new Event(id, name, description, startAt, endAt);
        }
    }
}
