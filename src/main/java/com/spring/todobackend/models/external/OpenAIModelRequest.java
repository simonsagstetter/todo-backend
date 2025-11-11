package com.spring.todobackend.models.external;

import lombok.Builder;

@Builder
public record OpenAIModelRequest( String model, String input ) {
}
