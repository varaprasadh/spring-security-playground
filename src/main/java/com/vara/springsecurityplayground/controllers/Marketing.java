package com.vara.springsecurityplayground.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Marketing {

    @GetMapping("/marketing")
    public String getContent() {
        return "This is markering site";
    }
}
