package com.spring.todobackend.services;

import com.spring.todobackend.dtos.TodoMapper;
import com.spring.todobackend.dtos.TodoWithoutIdDTO;
import com.spring.todobackend.exceptions.TodoHistoryNotFoundException;
import com.spring.todobackend.exceptions.TodoNotFoundException;
import com.spring.todobackend.models.Todo;
import com.spring.todobackend.models.TodoHistory;
import com.spring.todobackend.repositories.TodoHistoryRepository;
import com.spring.todobackend.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoHistoryRepository todoHistoryRepository;

    @Autowired
    private IdService idService;

    @Autowired
    private TodoMapper todoMapper;

    private enum HistoryKind {
        PREVIOUS,
        NEXT
    }

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
        String todoId = idService.generateIdFor( Todo.class.getSimpleName() );
        String todoHistoryId = idService.generateIdFor( TodoHistory.class.getSimpleName() );

        Todo newTodo = todoMapper.toTodo( todoId, 1, todo );
        TodoHistory newTodoHistory = todoMapper.toTodoHistory( todoHistoryId, newTodo );

        this.todoHistoryRepository.insert( newTodoHistory );
        return this.todoRepository.insert( newTodo );
    }

    // UPDATE SERVICES
    public Todo updateTodo( String id, TodoWithoutIdDTO todo ) throws TodoNotFoundException {
        Todo oldTodo = this.getTodo( id );
        String todoHistoryId = idService.generateIdFor( TodoHistory.class.getSimpleName() );

        Todo newTodo = todoMapper.toTodo( oldTodo.id(), oldTodo.currentVersion() + 1, todo );
        TodoHistory todoHistory = todoMapper.toTodoHistory( todoHistoryId, newTodo );

        this.todoHistoryRepository.insert( todoHistory );
        return this.todoRepository.save( newTodo );
    }

    // DELETE SERVICES
    public void deleteTodo( String id ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        Todo todoToDelete = this.getTodo( id );
        List<TodoHistory> todoHistories = this.todoHistoryRepository.findAllByTodoId( id )
                .orElseThrow( () -> new TodoHistoryNotFoundException( id ) );

        this.todoHistoryRepository.deleteAll( todoHistories );
        this.todoRepository.deleteById( todoToDelete.id() );
    }

    // VERSIONING SERVICES
    public Todo undo( String id ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        return this.updateFromTodoHistory( id, HistoryKind.PREVIOUS );
    }

    public Todo redo( String id ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        return this.updateFromTodoHistory( id, HistoryKind.NEXT );
    }

    private Todo updateFromTodoHistory( String id, HistoryKind historyKind ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        Todo currentTodo = this.getTodo( id );
        long currentVersion = currentTodo.currentVersion();
        long lookupVersion = historyKind == HistoryKind.NEXT ? currentVersion + 1 : currentVersion - 1;

        List<TodoHistory> todoHistoryList = this.todoHistoryRepository
                .findAllByTodoId( id )
                .orElseThrow( () -> new TodoHistoryNotFoundException( id ) );

        TodoHistory foundTodoHistory = todoHistoryList.stream()
                .filter( todoHistory -> todoHistory.version() == ( lookupVersion ) )
                .findFirst()
                .orElseThrow( () -> new TodoHistoryNotFoundException( id ) );

        Todo newTodo = todoMapper.toTodo( currentTodo.id(), foundTodoHistory );

        return this.todoRepository.save( newTodo );
    }


}
