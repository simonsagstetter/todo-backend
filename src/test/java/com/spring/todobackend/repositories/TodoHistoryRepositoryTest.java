package com.spring.todobackend.repositories;

import com.spring.todobackend.exceptions.TodoHistoryNotFoundException;
import com.spring.todobackend.models.TodoHistory;
import com.spring.todobackend.models.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TodoHistoryRepositoryTest {

    private static final TodoHistoryRepository todoHistoryRepository = Mockito.mock( TodoHistoryRepository.class );
    private static final String fixedTodoHistoryId = "TODO-HISTORY-TEST-ID";
    private static final String fixedTodoId = "TODO-TEST-ID";

    @BeforeEach
    void resetMocks() {
        Mockito.reset( todoHistoryRepository );
    }

    @Test
    void findAllByTodoId_ShouldThrow_WhenCalled() {
        //GIVEN

        //MOCKING
        Mockito.when( todoHistoryRepository.findAllByTodoId( fixedTodoId ) )
                .thenReturn( Optional.empty() );

        //WHEN
        TodoHistoryNotFoundException e = assertThrows(
                TodoHistoryNotFoundException.class,
                () -> todoHistoryRepository.findAllByTodoId( fixedTodoId ).orElseThrow(
                        () -> new TodoHistoryNotFoundException( fixedTodoId )
                )
        );

        //THEN
        assertThat( e )
                .isNotNull()
                .isInstanceOf( TodoHistoryNotFoundException.class );

        assertThat( e.getMessage() )
                .isNotNull()
                .isNotEmpty()
                .isEqualTo( new TodoHistoryNotFoundException( fixedTodoId ).getMessage() );

        //VERIFY
        Mockito.verify( todoHistoryRepository, Mockito.times( 1 ) ).findAllByTodoId( fixedTodoId );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todo.csv", delimiter = ',', numLinesToSkip = 1)
    void findAllByTodoId_ShouldNotThrow( String description, String status, String currentVersion ) {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        long version = Long.parseLong( currentVersion );

        TodoHistory todoHistory = TodoHistory.builder()
                .id( fixedTodoHistoryId )
                .todoId( fixedTodoId )
                .description( description )
                .status( todoStatus )
                .version( version )
                .build();

        List<TodoHistory> historyList = List.of( todoHistory );

        //MOCKING
        Mockito.when( todoHistoryRepository.findAllByTodoId( fixedTodoId ) )
                .thenReturn( Optional.of( historyList ) );

        //WHEN
        assertDoesNotThrow( () -> {
            List<TodoHistory> actual = todoHistoryRepository.findAllByTodoId( fixedTodoId )
                    .orElseThrow( () -> new TodoHistoryNotFoundException( fixedTodoId ) );
            //THEN
            assertThat( actual )
                    .isNotNull()
                    .isNotEmpty()
                    .contains( todoHistory );
        } );


        //VERIFY
        Mockito.verify( todoHistoryRepository, Mockito.times( 1 ) ).findAllByTodoId( fixedTodoId );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todo.csv", delimiter = ',', numLinesToSkip = 1)
    void deleteAll_ShouldNotThrow( String description, String status, String currentVersion ) {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        long version = Long.parseLong( currentVersion );

        TodoHistory todoHistory = TodoHistory.builder()
                .id( fixedTodoHistoryId )
                .todoId( fixedTodoId )
                .description( description )
                .status( todoStatus )
                .version( version )
                .build();

        List<TodoHistory> historyList = List.of( todoHistory );

        //MOCKING
        Mockito.when( todoHistoryRepository.findAllByTodoId( fixedTodoId ) )
                .thenReturn( Optional.of( historyList ) );

        Mockito.doNothing().when( todoHistoryRepository ).deleteAll( historyList );

        //WHEN AND THEN
        assertDoesNotThrow( () -> {
            List<TodoHistory> actual = todoHistoryRepository.findAllByTodoId( fixedTodoId )
                    .orElseThrow( () -> new TodoHistoryNotFoundException( fixedTodoId ) );

            todoHistoryRepository.deleteAll( actual );

        } );

        //VERIFY
        Mockito.verify( todoHistoryRepository, Mockito.times( 1 ) ).findAllByTodoId( fixedTodoId );
        Mockito.verify( todoHistoryRepository, Mockito.times( 1 ) ).deleteAll( historyList );

    }

}