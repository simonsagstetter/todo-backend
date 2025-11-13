package com.spring.todobackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAController {

    @GetMapping(value = "/board/todo")
    public String todo() {
        return "forward:/board/todo.html";
    }

    @GetMapping(value = "/board/doing")
    public String doing() {
        return "forward:/board/doing.html";
    }

    @GetMapping(value = "/board/done")
    public String done() {
        return "forward:/board/done.html";
    }


}
