package com.market.gandhi_market_price_tracker_api.exception.handler;

import com.market.gandhi_market_price_tracker_api.exception.custom.*;
import com.market.gandhi_market_price_tracker_api.exception.response.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildError(404, ex.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex, HttpServletRequest request) {
        return buildError(409, ex.getMessage(), request.getRequestURI(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest request) {
        return buildError(401, ex.getMessage(), request.getRequestURI(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MailDeliveryException.class)
    public ResponseEntity<ErrorResponse> handleMailFailure(
            MailDeliveryException ex,
            HttpServletRequest request) {

        return buildError(503, ex.getMessage(),
                request.getRequestURI(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        return buildError(500, "An unexpected error occurred. Please try again.",
                request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    // Helper
    private ResponseEntity<ErrorResponse> buildError(int status, String message, String path, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse(status, message, path, LocalDateTime.now());
        return ResponseEntity.status(httpStatus).body(error);
    }
}
