package com.spring.todobackend.models.external;

import java.util.List;

public record OpenAIModelResponse(
        OpenAIStatus status,
        List<OpenAIOutput> output
) {

    public boolean isValid() {
        return this.status() == OpenAIStatus.completed && this.output() != null && !this.output().isEmpty();
    }

    public OpenAIContent getContent() {
        return this.output
                .stream()
                .filter( output -> output.type().equals( OpenAIOutputType.message ) )
                .flatMap( output -> output.content().stream() )
                .filter( content -> !content.text().isEmpty() )
                .toList()
                .getFirst();
    }
}
