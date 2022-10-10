package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.exception.AuthorizationException;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFoundExceptionException(NotFoundException e, WebRequest request) {
        return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<?> handleAuthorizationExceptionException(NotFoundException e, WebRequest request) {
        return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleOtherExceptions(Exception e, WebRequest request) {
        System.out.println(e);
        return new ResponseEntity<Object>("Internal Server Error!", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
