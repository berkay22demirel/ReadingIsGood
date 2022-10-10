package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.exception.AuthorizationException;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(NotFoundException e, WebRequest request) {
        return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<?> handleAuthorizationException(NotFoundException e, WebRequest request) {
        return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(String.join("\n", validationErrors.values()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleOtherExceptions(Exception e, WebRequest request) {
        System.out.println(e);
        return new ResponseEntity<Object>("Internal Server Error!", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
