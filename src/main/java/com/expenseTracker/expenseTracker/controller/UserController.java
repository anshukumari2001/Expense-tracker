package com.expenseTracker.expenseTracker.controller;

import com.expenseTracker.expenseTracker.entity.Expense;
import com.expenseTracker.expenseTracker.entity.User;
import com.expenseTracker.expenseTracker.model.UserModel;
import com.expenseTracker.expenseTracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.createUser(userModel), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<User> readUser() {
        return new ResponseEntity<>(userService.readUser(), HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<HttpStatus> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.updateUser(userModel), HttpStatus.OK);
    }
}
