package com.spring.todobackend.models.external;

import lombok.Getter;

@Getter
public enum OpenAIModel {
    GPT_5_NANO( "gpt-5-nano" );

    private final String name;

    OpenAIModel( String name ) {
        this.name = name;
    }

    public static OpenAIModel fromName( String name ) {
        try {
            return OpenAIModel.valueOf( name );
        } catch ( IllegalArgumentException | NullPointerException e ) {
            return GPT_5_NANO;
        }
    }


}
