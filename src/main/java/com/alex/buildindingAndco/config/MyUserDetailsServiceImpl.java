package com.alex.buildindingAndco.config;

import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.model.User;
import com.alex.buildindingAndco.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            User existingUser = this.userService.getByUsername(username);
            return new MyUserDetails(existingUser);
        } catch (UnknownResourceException ure) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
    }
}
