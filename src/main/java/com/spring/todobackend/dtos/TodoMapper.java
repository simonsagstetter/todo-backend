package com.spring.todobackend.dtos;

import com.spring.todobackend.models.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoMapper() {}

    public Todo toTodo(String id, TodoWithoutIdDTO todoWithoutIdDTO){
        return Todo.builder()
                .id( id )
                .description( todoWithoutIdDTO.description() )
                .status(  todoWithoutIdDTO.status() )
                .build();
    }
}
