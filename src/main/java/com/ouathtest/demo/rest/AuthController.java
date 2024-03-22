package com.ouathtest.demo.rest;

import com.ouathtest.demo.config.TokenGenerator;
import com.ouathtest.demo.domain.dto.LoginDTO;
import com.ouathtest.demo.domain.dto.RegisterRequest;
import com.ouathtest.demo.domain.dto.TokenDTO;
import com.ouathtest.demo.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;


    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody RegisterRequest request) {
        User user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getEmail())
                .password(request.getPassword())
                .build();
        userDetailsManager.createUser(user);

        Authentication authentication = UsernamePasswordAuthenticationToken
                .authenticated(
                        user,
                        request.getPassword(),
                        Collections.emptyList()
                );
        return ResponseEntity.ok(tokenGenerator.createTokens(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                ));
        return ResponseEntity.ok(tokenGenerator.createTokens(authentication));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDTO> token(@RequestBody TokenDTO tokenDTO) {
        Authentication authentication = refreshTokenAuthProvider
                .authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));
        return ResponseEntity.ok(tokenGenerator.createTokens(authentication));
    }
}
