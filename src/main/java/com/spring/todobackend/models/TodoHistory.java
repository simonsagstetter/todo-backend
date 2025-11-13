package com.spring.todobackend.models;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@With
@Builder
@Document("todos_history")
public record TodoHistory(
        @Id String id,
        String todoId,
        String description,
        TodoStatus status,
        Boolean isGrammarChecked,
        Long version,
        Instant created,
        Instant lastModified
) {
}
