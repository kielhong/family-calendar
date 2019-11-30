package com.widehouse.calendar.user;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;

    @Test
    void findByUsername_GivenUsername_ReturnUser() {
        // given
        User expected = entityManager.persist(User.builder().username("username").name("name").build());
        // when
        Optional<User> actual = repository.findByUsername("username");
        // then
        then(actual).contains(expected);
    }
}