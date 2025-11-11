package com.spring.todobackend.models;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Builder
@Document("todos")
public record Todo( @Id String id, String description, TodoStatus status,
                    long currentVersion ) {
}
