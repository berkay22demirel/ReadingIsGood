package com.berkay22demirel.readingisgood.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/user")
@RestController
public class UserController {

    @GetMapping
    public String getUser() {
        return "Hello";
    }


}
