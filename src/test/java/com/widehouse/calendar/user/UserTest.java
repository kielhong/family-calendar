package com.widehouse.calendar.user;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

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
