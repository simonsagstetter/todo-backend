package com.spring.todobackend.services;

import com.spring.todobackend.dtos.TodoMapper;
import com.spring.todobackend.dtos.TodoDTO;
import com.spring.todobackend.exceptions.TodoHistoryNotFoundException;
import com.spring.todobackend.exceptions.TodoNotFoundException;
import com.spring.todobackend.models.Todo;
import com.spring.todobackend.models.TodoHistory;
import com.spring.todobackend.repositories.TodoHistoryRepository;
import com.spring.todobackend.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Todo createTodo( TodoDTO todo ) throws TodoHistoryNotFoundException, TodoNotFoundException {
        String todoId = this.idService.generateIdFor( Todo.class.getSimpleName() );
        String todoHistoryId = this.idService.generateIdFor( TodoHistory.class.getSimpleName() );

        Todo newTodo = this.todoMapper.toTodo( todoId, 1, todo );
        TodoHistory newTodoHistory = this.todoMapper.toTodoHistory( todoHistoryId, newTodo );

        TodoHistory insertedTodoHistory = this.todoHistoryRepository.insert( newTodoHistory );
        this.todoHistoryRepository.findById( insertedTodoHistory.id() )
                .orElseThrow( () -> new TodoHistoryNotFoundException( insertedTodoHistory.id() ) );

        Todo insertedTodo = todoRepository.insert( newTodo );
        return this.getTodo( insertedTodo.id() );
    }

    // UPDATE SERVICES
    @Transactional
    public Todo updateTodo( String id, TodoDTO todo ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        Todo oldTodo = this.getTodo( id );
        String todoHistoryId = this.idService.generateIdFor( TodoHistory.class.getSimpleName() );

        Todo newTodo = this.todoMapper.toTodo( oldTodo.id(), oldTodo.currentVersion() + 1, todo );
        TodoHistory todoHistory = this.todoMapper.toTodoHistory( todoHistoryId, newTodo );

        TodoHistory insertedTodoHistory = this.todoHistoryRepository.insert( todoHistory );
        this.todoHistoryRepository.findById( insertedTodoHistory.id() )
                .orElseThrow( () -> new TodoHistoryNotFoundException( insertedTodoHistory.id() ) );

        Todo updatedTodo = this.todoRepository.save( newTodo );
        return this.getTodo( updatedTodo.id() );
    }

    // DELETE SERVICES
    @Transactional
    public Todo deleteTodo( String id ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        Todo todoToDelete = this.getTodo( id );
        List<String> todoHistoryIds = this.todoHistoryRepository.findAllByTodoId( id )
                .orElseThrow( () -> new TodoHistoryNotFoundException( id ) )
                .stream()
                .map( TodoHistory::id )
                .toList();

        this.todoHistoryRepository.deleteAllById( todoHistoryIds );
        this.todoRepository.deleteById( todoToDelete.id() );

        boolean stillExists =
                this.todoRepository.findById( todoToDelete.id() ).isPresent() ||
                        this.todoHistoryRepository.findAllByTodoId( todoToDelete.id() ).isPresent();

        if ( stillExists ) {
            throw new IllegalStateException( "Delete failed: Todo or history still exists for id=" + id );
        }

        return todoToDelete;
    }

    // VERSIONING SERVICES
    @Transactional
    public Todo undo( String id ) throws TodoNotFoundException, TodoHistoryNotFoundException {
        return this.updateFromTodoHistory( id, HistoryKind.PREVIOUS );
    }

    @Transactional
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

        Todo newTodo = this.todoMapper.toTodo( currentTodo.id(), foundTodoHistory );

        return this.todoRepository.save( newTodo );
    }

}
