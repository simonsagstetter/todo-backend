package com.spring.todobackend.dtos;

import com.spring.todobackend.models.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.With;

@Builder
@With
public record TodoCreateDTO(
        @NotBlank(message = "Description cannot be blank") @Size(min = 3, max = 1024, message = "Description must contain at least 3 and at max 1024 characters") String description,
        TodoStatus status,
        Boolean checkGrammar
) {
}
