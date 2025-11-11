package com.spring.todobackend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.todobackend.models.GrammarResponse;
import com.spring.todobackend.models.external.OpenAIModel;
import com.spring.todobackend.models.external.OpenAIModelRequest;
import com.spring.todobackend.models.external.OpenAIModelResponse;
import com.spring.todobackend.models.external.OpenAIStatus;
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
                .append( "Please check the following words or sentences with the {{  }} placeholder for misspelling or grammar errors and return a correct version of the words or sentences as single string: " )
                .append( "{{ " )
                .append( inputString )
                .append( " }}" )
                .append( "The words or sentences can be any language and does not have to be english. Be aware of that. " )
                .append( "Your answer should only contain the corrected words or sentences as single string and nothing else. " )
                .append( "The answer format must be JSON. Here is an example: " )
                .append( "{ \"answer\": \"<correct>\" }" )
                .append( "<correct> is a placeholder for you answer. If you are not sure then return the words or sentences without changes in the placeholder." ).toString();
    }

    public Optional<GrammarResponse> checkGrammar( String inputString ) {
        OpenAIModelRequest request = OpenAIModelRequest.builder()
                .model( openAIModel.getName() )
                .input( this.createPrompt( inputString ) )
                .build();

        RestClient.ResponseSpec spec = restClient
                .post()
                .body( request )
                .retrieve();

        OpenAIModelResponse response = spec.body( OpenAIModelResponse.class );

        System.out.println( response );


        if ( response != null && response.status() == OpenAIStatus.completed && response.output() != null ) {
            try {
                return Optional.of( ( GrammarResponse ) this.objectMapper.readValue( response.getContent().text(), GrammarResponse.class ) );
            } catch ( JsonProcessingException e ) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
