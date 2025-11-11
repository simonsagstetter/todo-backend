package com.spring.todobackend.models;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationErrorResponse extends ErrorResponse {
    private List<FieldError> fieldErrors;
}
