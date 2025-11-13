package com.spring.todobackend.dtos;

import com.spring.todobackend.models.Todo;
import com.spring.todobackend.models.TodoHistory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TodoMapper {

    public TodoMapper() {
    }

    // Update
    public Todo toTodo( String id, long version, Instant created, Instant lastModified, TodoDTO todoDTO ) {
        return Todo.builder()
                .id( id )
                .description( todoDTO.description() )
                .status( todoDTO.status() )
                .isGrammarChecked( todoDTO.isGrammarChecked() )
                .currentVersion( version )
                .created( created )
                .lastModified( lastModified )
                .build();
    }

    // Create
    public Todo toTodo( String id, long version, Instant created, TodoCreateDTO todoDTO ) {
        return Todo.builder()
                .id( id )
                .description( todoDTO.description() )
                .status( todoDTO.status() )
                .isGrammarChecked( todoDTO.shouldGrammarCheck() )
                .currentVersion( version )
                .created( created )
                .lastModified( created )
                .build();
    }

    // History
    public Todo toTodo( String id, TodoHistory todoHistory ) {
        return Todo.builder()
                .id( id )
                .description( todoHistory.description() )
                .status( todoHistory.status() )
                .isGrammarChecked( todoHistory.isGrammarChecked() )
                .currentVersion( todoHistory.version() )
                .created( todoHistory.created() )
                .lastModified( todoHistory.lastModified() )
                .build();
    }

    public TodoHistory toTodoHistory( String id, Todo todo ) {
        return TodoHistory.builder()
                .id( id )
                .todoId( todo.id() )
                .description( todo.description() )
                .status( todo.status() )
                .isGrammarChecked( todo.isGrammarChecked() )
                .version( todo.currentVersion() )
                .created( todo.created() )
                .lastModified( todo.lastModified() )
                .build();
    }
}
