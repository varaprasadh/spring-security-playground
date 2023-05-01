package com.vara.springsecurityplayground.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class Dashboard {

    @GetMapping("/dashboard")
    public String getContent(Authentication authentication) {
        return "This is dashboard, hello " + getUserName(authentication);
    }

    private static String getUserName(Authentication authentication) {
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getFullName)
                .orElseGet(authentication::getName);

    }
}
