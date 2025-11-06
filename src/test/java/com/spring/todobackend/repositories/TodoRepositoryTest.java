package com.spring.todobackend.repositories;

import com.spring.todobackend.exceptions.TodoNotFoundException;
import com.spring.todobackend.models.Todo;
import com.spring.todobackend.models.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.tuple;

class TodoRepositoryTest {

    private static final TodoRepository todoRepository = Mockito.mock( TodoRepository.class );
    private static final String fixedTodoId = "TODO-TEST-ID";

    @BeforeEach
    void resetMocks() {
        Mockito.reset( todoRepository );
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenCalled() {
        //GIVEN
        List<Todo> todos = List.of();

        //MOCKING
        Mockito.when( todoRepository.findAll() )
                .thenReturn( todos );

        //WHEN
        List<Todo> actual = todoRepository.findAll();

        //THEN
        assertThat( actual )
                .isNotNull()
                .isEmpty();

        //VERIFY
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).findAll();
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todo.csv", delimiter = ',', numLinesToSkip = 1)
    void findAll_ShouldReturnTodoInList_WhenCalled( String description, String status, String currentVersion ) {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        long version = Long.parseLong( currentVersion );
        Todo newTodo = Todo.builder()
                .id( fixedTodoId )
                .description( description )
                .status( todoStatus )
                .currentVersion( version )
                .build();

        List<Todo> todos = List.of( newTodo );

        //MOCKING
        Mockito.when( todoRepository.findAll() )
                .thenReturn( todos );

        //WHEN
        List<Todo> actual = todoRepository.findAll();

        //THEN
        assertThat( actual )
                .isNotNull()
                .isNotEmpty()
                .contains( newTodo )
                .containsAll( todos );

        assertThat( actual.size() )
                .isEqualTo( todos.size() );

        assertThat( actual )
                .extracting( "id", "description", "status", "currentVersion" )
                .contains( tuple( fixedTodoId, description, todoStatus, version ) );


        //VERIFY
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).findAll();
    }

    @Test
    void findById_ShouldThrow_WhenCalled() {
        //Given

        //MOCKING
        Mockito.when( todoRepository.findById( fixedTodoId ) )
                .thenReturn( Optional.empty() );

        //WHEN
        TodoNotFoundException e = assertThrows( TodoNotFoundException.class, () -> {
            todoRepository.findById( fixedTodoId )
                    .orElseThrow( () -> new TodoNotFoundException( fixedTodoId ) );

        } );

        //THEN
        assertThat( e )
                .isNotNull()
                .isInstanceOf( TodoNotFoundException.class );

        assertThat( e.getMessage() )
                .isNotNull()
                .isNotEmpty()
                .isEqualTo( new TodoNotFoundException( fixedTodoId ).getMessage() );


        //VERIFY
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).findById( fixedTodoId );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todo.csv", delimiter = ',', numLinesToSkip = 1)
    void findById_ShouldNotThrow_WhenCalled( String description, String status, String currentVersion ) {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        long version = Long.parseLong( currentVersion );
        Todo newTodo = Todo.builder()
                .id( fixedTodoId )
                .description( description )
                .status( todoStatus )
                .currentVersion( version )
                .build();

        //MOCKING
        Mockito.when( todoRepository.findById( fixedTodoId ) )
                .thenReturn( Optional.of( newTodo ) );

        //WHEN
        assertDoesNotThrow( () -> {
            Todo todo = todoRepository.findById( fixedTodoId )
                    .orElseThrow( () -> new TodoNotFoundException( fixedTodoId ) );

            //THEN
            assertThat( todo )
                    .isNotNull()
                    .isEqualTo( newTodo );

        } );


        //VERIFY
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).findById( fixedTodoId );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todo.csv", delimiter = ',', numLinesToSkip = 1)
    void insert_ShouldReturnTodo_WhenCalled( String description, String status, String currentVersion ) {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        long version = Long.parseLong( currentVersion );
        Todo newTodo = Todo.builder()
                .id( fixedTodoId )
                .description( description )
                .status( todoStatus )
                .currentVersion( version )
                .build();

        //MOCKING
        Mockito.when( todoRepository.insert( newTodo ) )
                .thenReturn( newTodo );

        //WHEN
        Todo insertedTodo = todoRepository.insert( newTodo );

        //THEN
        assertThat( insertedTodo )
                .isNotNull()
                .isEqualTo( newTodo );

        //VERIFY
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).insert( newTodo );

    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todo.csv", delimiter = ',', numLinesToSkip = 1)
    void save_ShouldReturnTodo_WhenCalled( String description, String status, String currentVersion ) {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        long version = Long.parseLong( currentVersion );
        Todo newTodo = Todo.builder()
                .id( fixedTodoId )
                .description( description )
                .status( todoStatus )
                .currentVersion( version )
                .build();

        //MOCKING
        Mockito.when( todoRepository.insert( newTodo ) )
                .thenReturn( newTodo );

        Todo insertedTodo = todoRepository.insert( newTodo );
        Todo todoToUpdate = insertedTodo.withStatus( TodoStatus.IN_PROGRESS ).withCurrentVersion( 2 );


        Mockito.when( todoRepository.save( todoToUpdate ) )
                .thenReturn( todoToUpdate );

        //WHEN
        Todo updatedTodo = todoRepository.save( todoToUpdate );

        //THEN
        assertThat( updatedTodo )
                .isNotNull()
                .isEqualTo( todoToUpdate );

        //VERIFY
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).insert( newTodo );
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).save( todoToUpdate );

    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todo.csv", delimiter = ',', numLinesToSkip = 1)
    void delete_ShouldReturnNever_WhenCalled( String description, String status, String currentVersion ) {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        long version = Long.parseLong( currentVersion );
        Todo newTodo = Todo.builder()
                .id( fixedTodoId )
                .description( description )
                .status( todoStatus )
                .currentVersion( version )
                .build();

        //MOCKING
        Mockito.when( todoRepository.insert( newTodo ) )
                .thenReturn( newTodo );
        Todo insertedTodo = todoRepository.insert( newTodo );

        Mockito.doNothing().when( todoRepository ).deleteById( insertedTodo.id() );

        Mockito.when( todoRepository.findById( fixedTodoId ) ).thenReturn( Optional.empty() );

        //WHEN
        todoRepository.deleteById( insertedTodo.id() );

        //THEN
        TodoNotFoundException e = assertThrows( TodoNotFoundException.class, () -> {
            todoRepository.findById( fixedTodoId )
                    .orElseThrow( () -> new TodoNotFoundException( fixedTodoId ) );

        } );

        //THEN
        assertThat( e )
                .isNotNull()
                .isInstanceOf( TodoNotFoundException.class );

        assertThat( e.getMessage() )
                .isNotNull()
                .isNotEmpty()
                .isEqualTo( new TodoNotFoundException( fixedTodoId ).getMessage() );
        //VERIFY
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).insert( newTodo );
        Mockito.verify( todoRepository, Mockito.times( 1 ) ).deleteById( insertedTodo.id() );

    }

}