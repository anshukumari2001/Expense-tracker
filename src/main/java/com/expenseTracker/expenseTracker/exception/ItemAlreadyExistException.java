package com.expenseTracker.expenseTracker.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ItemAlreadyExistException extends RuntimeException {

    public ItemAlreadyExistException(String message) {
        super(message);
    }
}
