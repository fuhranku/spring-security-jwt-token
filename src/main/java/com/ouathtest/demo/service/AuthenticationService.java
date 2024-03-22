//package com.ouathtest.demo.service;
//
//import com.ouathtest.demo.domain.constant.Role;
//import com.ouathtest.demo.domain.dto.AuthenticationRequest;
//import com.ouathtest.demo.domain.dto.AuthenticationResponse;
//import com.ouathtest.demo.domain.dto.RegisterRequest;
//import com.ouathtest.demo.domain.entity.User;
//import com.ouathtest.demo.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//
//    private final UserRepository userRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final JwtService jwtService;
//
//    private final AuthenticationManager authenticationManager;
//
//    public AuthenticationResponse register(RegisterRequest request) {
//        User user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .username(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .createdDate(Instant.now())
//                .updatedDate(Instant.now())
//                .build();
//        if (!userRepository.existsByUsername(request.getEmail())) {
//            userRepository.save(user);
//            Map<String, Object> extraClaims = new HashMap<>();
//            extraClaims.put("first_name", user.getFirstName());
//            extraClaims.put("last_name", user.getLastName());
//            extraClaims.put("authority", user.getRole());
//
//            String jwtToken = jwtService.generateToken(extraClaims, user);
//            return AuthenticationResponse
//                    .builder()
//                    .token(jwtToken)
//                    .build();
//        } else {
//            throw new HttpClientErrorException(HttpStatus.PRECONDITION_FAILED,"User already exists");
//        }
//    }
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
//        );
//        User user = userRepository.findByUsername(request.getUserName()).orElseThrow();
//        String jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse
//                .builder()
//                .token(jwtToken)
//                .build();
//    }
//}
