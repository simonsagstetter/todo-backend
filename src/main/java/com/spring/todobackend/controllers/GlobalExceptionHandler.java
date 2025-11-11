package com.spring.todobackend.controllers;

import com.spring.todobackend.exceptions.TodoHistoryNotFoundException;
import com.spring.todobackend.exceptions.TodoNotFoundException;
import com.spring.todobackend.models.ErrorResponse;
import com.spring.todobackend.models.FieldError;
import com.spring.todobackend.models.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TodoNotFoundException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTodoNotFoundException( TodoNotFoundException e ) {
        return ErrorResponse.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( e.getMessage() )
                .build();
    }

    @ExceptionHandler(value = TodoHistoryNotFoundException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTodoHistoryNotFoundException( TodoHistoryNotFoundException e ) {
        return ErrorResponse.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( e.getMessage() )
                .build();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException( HttpRequestMethodNotSupportedException e ) {
        return ErrorResponse.builder()
                .status( HttpStatus.METHOD_NOT_ALLOWED )
                .message( e.getMessage() )
                .build();
    }

    @ExceptionHandler(value = NoResourceFoundException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoResourceFoundException( NoResourceFoundException e ) {
        return ErrorResponse.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( e.getMessage() )
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValidException( MethodArgumentNotValidException e ) {

        return ValidationErrorResponse.builder()
                .status( HttpStatus.BAD_REQUEST )
                .message( e.getMessage() )
                .fieldErrors(
                        e.getBindingResult().getFieldErrors()
                                .stream().map( error -> new FieldError( error.getField(), error.getDefaultMessage() ) )
                                .toList()
                )
                .build();
    }
}
