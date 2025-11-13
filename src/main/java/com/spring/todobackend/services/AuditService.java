package com.spring.todobackend.services;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AuditService {

    public AuditService() {
    }

    public Instant getCurrentTimestamp() {
        return Instant.now();
    }
    
}
