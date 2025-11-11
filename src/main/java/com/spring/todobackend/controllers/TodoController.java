package com.spring.todobackend.controllers;

import com.spring.todobackend.dtos.TodoDTO;
import com.spring.todobackend.exceptions.TodoHistoryNotFoundException;
import com.spring.todobackend.exceptions.TodoNotFoundException;
import com.spring.todobackend.models.Todo;
import com.spring.todobackend.services.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
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

    @GetMapping(path = "", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> getAll() {
        return this.todoService.getAllTodos();
    }

    @GetMapping(path = "/{id}", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Todo get( @PathVariable String id ) throws TodoNotFoundException {
        return this.todoService.getTodo( id );
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create( @Valid @RequestBody TodoDTO todoDTO )
            throws TodoNotFoundException, TodoHistoryNotFoundException {
        return this.todoService.createTodo( todoDTO );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Todo update( @PathVariable String id, @Valid @RequestBody TodoDTO todoDTO )
            throws TodoNotFoundException, TodoHistoryNotFoundException {
        return this.todoService.updateTodo( id, todoDTO );
    }

    @PostMapping(path = "/{id}/undo", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Todo undo( @PathVariable String id ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        return this.todoService.undo( id );
    }

    @PostMapping(path = "/{id}/redo", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Todo redo( @PathVariable String id ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        return this.todoService.redo( id );
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Todo delete( @PathVariable String id ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        return this.todoService.deleteTodo( id );
    }

}
