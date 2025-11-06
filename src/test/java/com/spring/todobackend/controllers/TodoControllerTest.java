package com.spring.todobackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.todobackend.dtos.TodoDTO;
import com.spring.todobackend.dtos.TodoMapper;
import com.spring.todobackend.models.Todo;
import com.spring.todobackend.models.TodoStatus;
import com.spring.todobackend.services.TodoService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoMapper todoMapper;

    private static final String fixedTodoId = "TODO-TEST-ID";

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void getAll_ShouldReturnTodo_WhenCalled( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo );

        String jsonContent = new ObjectMapper().writeValueAsString( List.of( todo ) );

        mockMvc.perform( MockMvcRequestBuilders
                        .get( "/api/todo" )
                )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$[0].id" ).isNotEmpty() )
                .andExpect( MockMvcResultMatchers.jsonPath( "$[0].currentVersion" ).value( 1L ) );


    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void get_ShouldReturn404_WhenCalled( String description, String status, String currentVersion ) throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get( "/api/todo/" + fixedTodoId )
                )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );


    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void get_ShouldReturnTodo_WhenCalled( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo );

        String jsonContent = new ObjectMapper().writeValueAsString( todo );

        mockMvc.perform( MockMvcRequestBuilders
                        .get( "/api/todo/" + todo.id() )
                )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.id" ).value( todo.id() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.description" ).value( todo.description() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( todo.status().toString() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.currentVersion" ).value( todo.currentVersion() ) );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void create_ShouldReturnTodo_WhenCalled( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        String jsonContent = new ObjectMapper().writeValueAsString( newTodo );

        mockMvc.perform( MockMvcRequestBuilders
                        .post( "/api/todo" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                        .content( jsonContent )
                )
                .andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.id" ).isNotEmpty() )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.description" ).value( newTodo.description() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( newTodo.status().toString() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.currentVersion" ).value( 1L ) );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void update_ShouldReturnTodo_WhenCalled( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo );

        TodoDTO updatedTodo = TodoDTO.builder()
                .description( "updated" )
                .status( TodoStatus.IN_PROGRESS )
                .build();

        String jsonContent = new ObjectMapper().writeValueAsString( updatedTodo );

        mockMvc.perform( MockMvcRequestBuilders
                        .put( "/api/todo/" + todo.id() )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                        .content( jsonContent )
                )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.id" ).value( todo.id() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.description" ).value( updatedTodo.description() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( updatedTodo.status().toString() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.currentVersion" ).value( 2L ) );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void delete_ShouldNotThrow_WhenCalled( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo );

        String jsonContent = new ObjectMapper().writeValueAsString( todo );

        mockMvc.perform( MockMvcRequestBuilders
                        .delete( "/api/todo/" + todo.id() )
                )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) );

        mockMvc.perform( MockMvcRequestBuilders
                        .get( "/api/todo/" + todo.id() )
                )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void undo_redo_ShouldReturnTodoFromHistory_WhenCalled( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo );

        TodoDTO todoToUpdate = TodoDTO.builder()
                .description( "updated" )
                .status( TodoStatus.IN_PROGRESS )
                .build();

        Todo updatedTodo = todoService.updateTodo( todo.id(), todoToUpdate );

        String jsonContentPrev = new ObjectMapper().writeValueAsString( todo );
        String jsonContentNext = new ObjectMapper().writeValueAsString( updatedTodo );

        mockMvc.perform( MockMvcRequestBuilders
                        .post( "/api/todo/" + todo.id() + "/undo" )
                )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContentPrev ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.id" ).value( todo.id() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.description" ).value( todo.description() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( todo.status().toString() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.currentVersion" ).value( todo.currentVersion() ) );

        mockMvc.perform( MockMvcRequestBuilders
                        .post( "/api/todo/" + todo.id() + "/redo" )
                )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContentNext ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.id" ).value( updatedTodo.id() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.description" ).value( updatedTodo.description() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( updatedTodo.status().toString() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.currentVersion" ).value( updatedTodo.currentVersion() ) );
    }

}