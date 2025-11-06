package com.spring.todobackend.exceptions;

public class TodoHistoryNotFoundException extends Exception {
    public TodoHistoryNotFoundException( String id ) {
        super( "No history found for todo with id: " + id );
    }
}
