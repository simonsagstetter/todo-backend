package com.spring.todobackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.todobackend.dtos.TodoDTO;
import com.spring.todobackend.exceptions.TodoHistoryNotFoundException;
import com.spring.todobackend.models.ErrorResponse;
import com.spring.todobackend.models.Todo;
import com.spring.todobackend.models.TodoStatus;
import com.spring.todobackend.services.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private TodoService todoService;

    private static final String fixedTodoId = "TODO-TEST-ID";


    @Test
    void global_ShouldReturn404_WhenCalledOnNotExistingResource() throws Exception {
        String path = "api/todos";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( "No static resource " + path + "." )
                .build();

        String jsonContent = new ObjectMapper().writeValueAsString( errorResponse );

        mockMvc.perform( MockMvcRequestBuilders
                        .get( "/" + path )
                )
                .andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( errorResponse.getStatus().name() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( errorResponse.getMessage() ) );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void global_ShouldReturn405_WhenCalledWithNotAllowedMethod( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo, true );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status( HttpStatus.METHOD_NOT_ALLOWED )
                .message( "Request method 'GET' is not supported" )
                .build();

        String jsonContent = new ObjectMapper().writeValueAsString( errorResponse );

        mockMvc.perform( MockMvcRequestBuilders
                        .get( "/api/todo/" + todo.id() + "/undo" )
                )
                .andExpect( MockMvcResultMatchers.status().isMethodNotAllowed() )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( errorResponse.getStatus().name() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( errorResponse.getMessage() ) );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void getAll_ShouldReturnTodo_WhenCalled( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo, true );

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

        Todo todo = todoService.createTodo( newTodo, true );

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

        mockRestServiceServer.expect( ExpectedCount.once(), MockRestRequestMatchers.requestTo( "https://api.openai.com/v1/responses" ) )
                .andExpect( MockRestRequestMatchers.method( HttpMethod.POST ) )
                .andRespond( MockRestResponseCreators.withSuccess().contentType( MediaType.APPLICATION_JSON )
                        .body( """
                                        {
                                          "status": "completed",
                                          "output": [
                                            {
                                              "type": "message",
                                              "status": "completed",
                                              "content": [
                                                {
                                                  "text": "{\\"answer\\": \\"TEST\\"}"
                                                }
                                              ]
                                            }
                                          ]
                                        }
                                """ ) );

        mockMvc.perform( MockMvcRequestBuilders
                        .post( "/api/todo" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                        .content( jsonContent )
                )
                .andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.id" ).isNotEmpty() )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.description" ).value( "TEST" ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( newTodo.status().toString() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.currentVersion" ).value( 1L ) );

        mockRestServiceServer.verify();
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void create_ShouldReturnValidationErrorResponse_WhenCalledWithInvalidBlankTodoDTO( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( "       " )
                .status( todoStatus )
                .build();

        String jsonContent = new ObjectMapper().writeValueAsString( newTodo );

        mockMvc.perform( MockMvcRequestBuilders
                        .post( "/api/todo" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                        .content( jsonContent )
                )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( HttpStatus.BAD_REQUEST.name() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.fieldErrors" ).isArray() );
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void create_ShouldReturnValidationErrorResponse_WhenCalledWithInvalidLengthTodoDTO( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( "1" )
                .status( todoStatus )
                .build();

        String jsonContent = new ObjectMapper().writeValueAsString( newTodo );

        mockMvc.perform( MockMvcRequestBuilders
                        .post( "/api/todo" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                        .content( jsonContent )
                )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( HttpStatus.BAD_REQUEST.name() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.fieldErrors" ).isArray() );
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

        Todo todo = todoService.createTodo( newTodo, true );

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

        Todo todo = todoService.createTodo( newTodo, true );

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

        Todo todo = todoService.createTodo( newTodo, true );

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

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void undo_ShouldReturn404_WhenCalledOnTodoWithoutHistory( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo, true );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( new TodoHistoryNotFoundException( todo.id() ).getMessage() )
                .build();

        String jsonContent = new ObjectMapper().writeValueAsString( errorResponse );

        mockMvc.perform( MockMvcRequestBuilders
                        .post( "/api/todo/" + todo.id() + "/undo" )
                )
                .andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( errorResponse.getStatus().name() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( errorResponse.getMessage() ) );

    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/todos.csv", delimiter = ',', numLinesToSkip = 1)
    void redo_ShouldReturn404_WhenCalledOnTodoWithoutHistory( String description, String status ) throws Exception {
        //GIVEN
        TodoStatus todoStatus = TodoStatus.valueOf( status );
        TodoDTO newTodo = TodoDTO.builder()
                .description( description )
                .status( todoStatus )
                .build();

        Todo todo = todoService.createTodo( newTodo, true );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status( HttpStatus.NOT_FOUND )
                .message( new TodoHistoryNotFoundException( todo.id() ).getMessage() )
                .build();

        String jsonContent = new ObjectMapper().writeValueAsString( errorResponse );

        mockMvc.perform( MockMvcRequestBuilders
                        .post( "/api/todo/" + todo.id() + "/redo" )
                )
                .andExpect( MockMvcResultMatchers.status().isNotFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.content().json( jsonContent ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.status" ).value( errorResponse.getStatus().name() ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( errorResponse.getMessage() ) );

    }

}