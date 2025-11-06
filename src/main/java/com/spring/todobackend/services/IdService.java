package com.spring.todobackend.services;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdService {

    public IdService() {}

    public String generateIdFor(String recordName){
        return recordName.toUpperCase() + UUID.randomUUID().toString().replaceAll("-","");
    }
}
