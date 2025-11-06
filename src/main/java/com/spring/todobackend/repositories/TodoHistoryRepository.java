package com.spring.todobackend.repositories;

import com.spring.todobackend.models.TodoHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoHistoryRepository extends MongoRepository<TodoHistory, String> {
    Optional<List<TodoHistory>> findAllByTodoId( String todoId );

}
