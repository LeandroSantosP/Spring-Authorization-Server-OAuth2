package com.leandrosps.authserver.infra.controllers;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {
    @GetMapping("/get")
    public String getMethodName() {
        return new String();
    }

}
