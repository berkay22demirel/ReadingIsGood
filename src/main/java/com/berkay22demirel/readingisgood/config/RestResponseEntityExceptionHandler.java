package com.berkay22demirel.readingisgood.config;

import com.berkay22demirel.readingisgood.exception.AuthorizationException;
import com.berkay22demirel.readingisgood.exception.BusinessException;
import com.berkay22demirel.readingisgood.exception.NoStockException;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.error("An not found error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<?> handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        log.error("An authorization error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NoStockException.class})
    public ResponseEntity<?> handleNoStockException(NoStockException e, HttpServletRequest request) {
        log.error("An no stock error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("An business error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
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
    public ResponseEntity<?> handleOtherExceptions(Exception e, HttpServletRequest request) {
        log.error("An error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<Object>("Internal Server Error!", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
