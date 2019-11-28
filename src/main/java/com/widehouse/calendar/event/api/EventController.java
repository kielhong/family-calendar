package com.widehouse.calendar.event.api;

import com.widehouse.calendar.event.data.Event;
import com.widehouse.calendar.event.service.EventService;
import com.widehouse.calendar.user.User;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("events")
public class EventController {
    private final EventService eventService;

    @GetMapping("daily")
    public List<Event> getEventsByDaily(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        User user = User.builder().name("user").build();

        return eventService.findByDate(user, date, ZoneOffset.UTC);
    }
}
