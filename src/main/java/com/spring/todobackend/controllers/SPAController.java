package com.spring.todobackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAController {

    @GetMapping(value = { "/board/**", "/details/**", "/edit/**" })
    public String forward() {
        return "forward:/index.html";
    }
}
