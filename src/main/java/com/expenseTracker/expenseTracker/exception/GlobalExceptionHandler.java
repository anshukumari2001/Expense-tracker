package com.expenseTracker.expenseTracker.exception;

import com.expenseTracker.expenseTracker.entity.ErrorResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponse errorResponse= new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDate(new Date());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        ErrorResponse errorResponse= new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDate(new Date());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @Override protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {
        Map<String, Object> body= new HashMap<>();
        List<String> error=
            ex.getBindingResult().getFieldErrors().stream().map(x-> x.getDefaultMessage()).toList();
        body.put("errorMessage ", error);
        body.put("StatusCode", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex){
        ErrorResponse errorResponse= new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDate(new Date());
        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ ItemAlreadyExistException.class})
    public ResponseEntity<ErrorResponse> handleItemAlreadyExistException(ItemAlreadyExistException ex){
        ErrorResponse errorResponse= new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDate(new Date());
        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
