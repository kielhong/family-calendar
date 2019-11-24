package com.widehouse.calendar.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class UserTest {
    @Test
    void builderTest() {
        User user = User.builder()
                .name("user")
                .build();
        // then
        then(user)
                .hasFieldOrPropertyWithValue("name", "user");
    }

}
