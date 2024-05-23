package com.expenseTracker.expenseTracker.entity;

import java.util.Date;
import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private Integer statusCode;
    private Date date;
}
