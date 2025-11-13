package com.spring.todobackend.dtos;

import com.spring.todobackend.models.Todo;
import com.spring.todobackend.models.TodoHistory;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoMapper() {
    }

    public Todo toTodo( String id, long version, TodoDTO todoDTO ) {
        return Todo.builder()
                .id( id )
                .description( todoDTO.description() )
                .status( todoDTO.status() )
                .currentVersion( version )
                .build();
    }

    public Todo toTodo( String id, long version, TodoCreateDTO todoDTO ) {
        return Todo.builder()
                .id( id )
                .description( todoDTO.description() )
                .status( todoDTO.status() )
                .currentVersion( version )
                .build();
    }

    public Todo toTodo( String id, TodoHistory todoHistory ) {
        return Todo.builder()
                .id( id )
                .description( todoHistory.description() )
                .status( todoHistory.status() )
                .currentVersion( todoHistory.version() )
                .build();
    }

    public TodoHistory toTodoHistory( String id, Todo todo ) {
        return TodoHistory.builder()
                .id( id )
                .todoId( todo.id() )
                .description( todo.description() )
                .status( todo.status() )
                .version( todo.currentVersion() )
                .build();
    }
}
