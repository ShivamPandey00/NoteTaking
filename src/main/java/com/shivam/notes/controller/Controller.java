package com.shivam.notes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {



    @GetMapping("/healthcheck")
    public String healthCheck(){
        return "project is running";
    }

}
