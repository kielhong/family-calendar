package com.widehouse.calendar.user;

import java.util.Arrays;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class EventUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User(" + username + ") not exist"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), "",
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }
}