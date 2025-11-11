package com.spring.todobackend.models;


import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Getter
@SuperBuilder
public class ErrorResponse {

    private HttpStatus status;
    private String message;
}
