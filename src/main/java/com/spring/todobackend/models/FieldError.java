package com.spring.todobackend.models;

import lombok.Builder;

@Builder
public record FieldError( String field, String message ) {

}