package com.vara.springsecurityplayground.filters;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

public class RobotLoginConfigurer extends AbstractHttpConfigurer<RobotLoginConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity builder) throws Exception {
        // step1, initializes bunch of objects
        // set authentication providers,
        builder.authenticationProvider(
                new RobotAuthenticationProvider(List.of("beep"))
        );

    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        // initializes bunch of objects, but reused objects from step 1 and other configurers
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilterBefore(new RobotFilter(authenticationManager), FilterSecurityInterceptor.class);

    }
}
