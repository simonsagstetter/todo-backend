package com.spring.todobackend.exceptions;

public class TodoNotFoundException extends Exception {
    public TodoNotFoundException( String id ) {
        super( "Could not find todo with id: " + id );
    }
}
