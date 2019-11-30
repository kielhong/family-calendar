package com.widehouse.calendar.user;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class EventUserDetailsServiceTest {
    private EventUserDetailsService service;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        service = new EventUserDetailsService(userRepository);
    }

    @Test
    @DisplayName("username 에 해당하는 UserDetail 을 반환한다")
    void loadUser_GivenUsername_ThenReturnUserDetails() {
        // given
        final String username = "550e8400-e29b-41d4-a716-446655440000";
        User user = User.builder()
                .username(username)
                .name("user")
                .build();
        given(userRepository.findByUsername(anyString()))
                .willReturn(Optional.of(user));
        // when
        UserDetails actual = service.loadUserByUsername(username);
        // then
        then(actual)
                .hasFieldOrPropertyWithValue("username", username);
        then(actual.getAuthorities())
                .extracting("role")
                .contains("USER");
    }

    @Test
    @DisplayName("존재하지 않는 username 의 경우 UsernameNotFoundException 을 일으킨다")
    void loadUser_GivenNotExistsUser_ThrowUsernameNotFoundException() {
        // given
        given(userRepository.findByUsername(anyString()))
                .willReturn(Optional.empty());
        // expect
        thenThrownBy(() -> service.loadUserByUsername("550e8400-e29b-41d4-a716-446655440000"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}