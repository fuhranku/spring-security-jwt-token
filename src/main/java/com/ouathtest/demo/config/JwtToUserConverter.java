package com.ouathtest.demo.config;

import com.ouathtest.demo.domain.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        User user = new User();
        String userId = jwt.getSubject();
        user.setId(Long.parseLong(userId));
        return new UsernamePasswordAuthenticationToken(user, jwt, Collections.emptyList());
    }
}
