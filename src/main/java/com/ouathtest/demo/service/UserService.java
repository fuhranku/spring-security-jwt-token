package com.ouathtest.demo.service;

import com.ouathtest.demo.domain.constant.RoleType;
import com.ouathtest.demo.domain.dto.RegisterRequest;
import com.ouathtest.demo.domain.entity.RoleEntity;
import com.ouathtest.demo.domain.entity.UserEntity;
import com.ouathtest.demo.repository.RoleRepository;
import com.ouathtest.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final List<String> defaultRoleTypes = List.of(RoleType.USER.toString());

    public UserEntity registerUser(RegisterRequest request) {
        if (!userRepository.existsByUsername(request.getEmail())) {
            List<RoleEntity> defaultRoleEntities = roleRepository.findAllByNameIn(defaultRoleTypes);
            UserEntity userEntity = UserEntity.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .username(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(defaultRoleEntities)
                    .createdDate(Instant.now())
                    .updatedDate(Instant.now())
                    .build();
            log.info(
                    "Registering user: {} {} {}",
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.getUsername()
            );
            return userRepository.save(userEntity);
        } else {
            throw new HttpClientErrorException(HttpStatus.PRECONDITION_FAILED, "User already exists");
        }
    }

    public RoleEntity saveRole(RoleEntity roleEntity) {
        log.info("Saving role: {}", roleEntity);
        return roleRepository.save(roleEntity);
    }

    public void addRoleToUser(String username, String roleName) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        RoleEntity roleEntity = roleRepository.findByName(roleName).orElseThrow();
        userEntity.getRoles().add(roleEntity);
        log.info("Adding role {} to user {}", roleName, username);
        userRepository.save(userEntity);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
