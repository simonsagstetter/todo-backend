package com.spring.todobackend.dtos;

import com.spring.todobackend.models.TodoStatus;
import lombok.Builder;

@Builder
public record TodoWithoutIdDTO( String description, TodoStatus status ) {
}
