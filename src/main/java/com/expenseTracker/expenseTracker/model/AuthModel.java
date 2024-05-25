package com.expenseTracker.expenseTracker.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthModel {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
