package com.expenseTracker.expenseTracker.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {
    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotNull(message = "Email should not be null")
    @Email(message = "Email format allowed")
    private String email;

    @Size(min=3, message = "Atleast 3 size password allowed")
    @NotNull(message = "Password should not be null")
    private String password;

    @NotNull(message = "age should not be null")
    private Integer age;
}
