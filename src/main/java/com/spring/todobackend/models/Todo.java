package com.spring.todobackend.models;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@With
@Builder
@Document("todos")
public record Todo(
        @Id String id,
        String description,
        TodoStatus status,
        Boolean isGrammarChecked,
        long currentVersion,
        @CreatedDate Instant created,
        @LastModifiedDate Instant lastModified
) {
}
