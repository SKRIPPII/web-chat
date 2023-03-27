package com.example.MyFirstRestApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class FirstRestController {
    @GetMapping("/hello")
    public String sayHello(){
        return "simple/hello";
    }
    @GetMapping("/admin")
    public String admin(){
        return "simple/admin";
    }
}
