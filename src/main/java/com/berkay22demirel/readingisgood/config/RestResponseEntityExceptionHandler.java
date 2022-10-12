package com.berkay22demirel.readingisgood.config;

import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.exception.AuthorizationException;
import com.berkay22demirel.readingisgood.exception.BusinessException;
import com.berkay22demirel.readingisgood.exception.NoStockException;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Response> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.error("An not found error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<Response> handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        log.error("An authorization error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NoStockException.class})
    public ResponseEntity<Response> handleNoStockException(NoStockException e, HttpServletRequest request) {
        log.error("An no stock error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Response> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("An business error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Response> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        return new ResponseEntity<>(new Response("Email or password is incorrect!"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handle(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(new Response("Method argument not valid!", validationErrors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> handleOtherExceptions(Exception e, HttpServletRequest request) {
        log.error("An error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<>(new Response("Internal Server Error!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
