package com.widehouse.calendar.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.widehouse.calendar.event.data.Event;
import com.widehouse.calendar.event.service.EventService;
import com.widehouse.calendar.user.User;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EventController.class)
class EventControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService eventService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().name("user").build();
    }

    @Test
    @WithMockUser
    void listEventByDaily() throws Exception {
        // given
        Event event1 = Event.builder()
                .creator(user)
                .name("event1").description("event1")
                .startAt(ZonedDateTime.of(2019, 11, 15, 9, 0, 0, 0, ZoneOffset.UTC))
                .endAt(ZonedDateTime.of(2019, 11, 15, 10, 0, 0, 0, ZoneOffset.UTC))
                .build();
        Event event2 = Event.builder()
                .creator(user)
                .name("event2").description("event2")
                .startAt(ZonedDateTime.of(2019, 11, 15, 13, 0, 0, 0, ZoneOffset.UTC))
                .endAt(ZonedDateTime.of(2019, 11, 15, 14, 0, 0, 0, ZoneOffset.UTC))
                .build();
        given(eventService.findByDate(any(User.class), any(LocalDate.class), any(ZoneOffset.class)))
                .willReturn(Arrays.asList(event1, event2));
        // when
        mvc.perform(get("/events/daily").param("date", "2019-11-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        // then
        verify(eventService).findByDate(any(User.class), any(LocalDate.class), any(ZoneOffset.class));
    }
}
