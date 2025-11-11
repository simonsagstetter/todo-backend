package com.spring.todobackend.models.external;

import java.util.List;

public record OpenAIOutput( OpenAIStatus status, OpenAIOutputType type, List<OpenAIContent> content ) {
}
