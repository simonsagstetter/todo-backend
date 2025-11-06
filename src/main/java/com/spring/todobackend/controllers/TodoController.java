package com.spring.todobackend.controllers;

import com.spring.todobackend.dtos.TodoWithoutIdDTO;
import com.spring.todobackend.exceptions.TodoNotFoundException;
import com.spring.todobackend.models.Todo;
import com.spring.todobackend.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/todo",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = {
                "Content-Type=" + MediaType.APPLICATION_JSON_VALUE,
                "Accept=" + MediaType.APPLICATION_JSON_VALUE
        }
)
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(path = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> getAll() {
        return this.todoService.getAllTodos();
    }

    @GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Todo get( @PathVariable String id ) {
        try {
            return this.todoService.getTodo( id );
        } catch ( TodoNotFoundException e ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, e.getMessage(), e );
        }
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create( @RequestBody TodoWithoutIdDTO todoWithoutIdDTO ) {
        return this.todoService.createTodo( todoWithoutIdDTO );
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Todo update( @PathVariable String id, @RequestBody TodoWithoutIdDTO todoWithoutIdDTO ) {
        try {
            return this.todoService.updateTodo( id, todoWithoutIdDTO );
        } catch ( TodoNotFoundException e ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, e.getMessage(), e );
        }
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable String id ) {
        try {
            this.todoService.deleteTodo( id );
        } catch ( TodoNotFoundException e ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, e.getMessage(), e );
        }
    }

}
