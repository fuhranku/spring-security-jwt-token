package com.ouathtest.demo.service;

import com.ouathtest.demo.domain.constant.Role;
import com.ouathtest.demo.domain.entity.User;
import com.ouathtest.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;

@Service
public class UserManager implements UserDetailsManager {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        ((User) user).setPassword(passwordEncoder.encode(user.getPassword()));
        ((User) user).setRole(Role.USER);
        ((User) user).setCreatedDate(Instant.now());
        ((User) user).setUpdatedDate(Instant.now());
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new IllegalArgumentException(
                            MessageFormat.format("User with username {0} already exists", u.getUsername())
                    );
                });
        userRepository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format("User with username {0} not found", username
                        )));
    }
}
