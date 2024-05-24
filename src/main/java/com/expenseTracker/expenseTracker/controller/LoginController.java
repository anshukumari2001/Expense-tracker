package com.expenseTracker.expenseTracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/login")
    public ResponseEntity<String> createUser() {
        return new ResponseEntity<>("User Login Successfully", HttpStatus.CREATED) ;
    }
}
