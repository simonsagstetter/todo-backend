package com.spring.todobackend.services;

import com.spring.todobackend.dtos.TodoMapper;
import com.spring.todobackend.dtos.TodoWithoutIdDTO;
import com.spring.todobackend.exceptions.TodoNotFoundException;
import com.spring.todobackend.models.Todo;
import com.spring.todobackend.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private IdService idService;

    @Autowired
    private TodoMapper todoMapper;

    // GET SERVICES
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodo( String id ) throws TodoNotFoundException {
        return todoRepository
                .findById( id )
                .orElseThrow( () -> new TodoNotFoundException( id ) );
    }

    // CREATE SERVICES
    public Todo createTodo( TodoWithoutIdDTO todo ) {
        String newRecordId = idService.generateIdFor( Todo.class.getSimpleName() );
        Todo newTodo = todoMapper.toTodo( newRecordId, todo );
        return this.todoRepository.insert( newTodo );
    }

    // UPDATE SERVICES
    public Todo updateTodo( String id, TodoWithoutIdDTO todo ) throws TodoNotFoundException {
        Todo oldTodo = this.getTodo( id );
        Todo newTodo = todoMapper.toTodo( oldTodo.id(), todo );
        return this.todoRepository.save( newTodo );
    }

    // DELETE SERVICES
    public void deleteTodo( String id ) throws TodoNotFoundException {
        Todo todoToDelete = this.getTodo( id );
        this.todoRepository.deleteById( todoToDelete.id() );
    }


}
