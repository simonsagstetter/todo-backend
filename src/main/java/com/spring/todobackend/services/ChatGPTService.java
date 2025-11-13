package com.spring.todobackend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.todobackend.models.GrammarResponse;
import com.spring.todobackend.models.external.OpenAIModel;
import com.spring.todobackend.models.external.OpenAIModelRequest;
import com.spring.todobackend.models.external.OpenAIModelResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;


@Service
public class ChatGPTService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final OpenAIModel openAIModel;

    public ChatGPTService( Environment environment, RestClient.Builder restClientBuilder, ObjectMapper objectMapper ) {
        this.restClient = restClientBuilder
                .baseUrl( "https://api.openai.com/v1/responses" )
                .defaultHeader( "Accept", "application/json" )
                .defaultHeader( "Content-Type", "application/json" )
                .defaultHeader( "Authorization", "Bearer " + environment.getProperty( "openai.api.key" ) )
                .build();
        this.objectMapper = objectMapper;
        this.openAIModel = OpenAIModel.fromName( environment.getProperty( "openai.api.model" ) );
    }

    private String createPrompt( String inputString ) {
        return new StringBuilder()
                .append( "Please check the following string within the double open and closed curley brackets for misspelling or grammar errors: " )
                .append( "{{ " )
                .append( inputString )
                .append( "}}. " )
                .append( "If you think the spelling or grammar is wrong, create a correct version the string otherwise use the original string. " )
                .append( "Finally put the string - corrected or original - into the answer property value of the following JSON and return the JSON in your response. " )
                .append( "{ \"answer\": \"string\" }" ).toString();
    }

    public Optional<GrammarResponse> checkGrammar( String inputString ) {
        OpenAIModelRequest request = OpenAIModelRequest.builder()
                .model( openAIModel.getName() )
                .input( this.createPrompt( inputString ) )
                .build();

        OpenAIModelResponse response = restClient
                .post()
                .body( request )
                .retrieve().body( OpenAIModelResponse.class );

        if ( response != null && response.isValid() ) {
            try {
                return Optional.of( ( GrammarResponse ) this.objectMapper.readValue( response.getContent().text(), GrammarResponse.class ) );
            } catch ( JsonProcessingException e ) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
