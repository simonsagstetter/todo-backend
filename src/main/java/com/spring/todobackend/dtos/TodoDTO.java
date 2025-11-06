package com.spring.todobackend.dtos;

import com.spring.todobackend.models.TodoStatus;
import lombok.Builder;

@Builder
public record TodoDTO( String description, TodoStatus status ) {
}
