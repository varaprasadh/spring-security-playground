package com.vara.springsecurityplayground.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Analytics {

    @GetMapping("/analytics")
    public String getContent() {
        return "this is analytics";
    }
}
