package com.example.MyFirstRestApp.controllers;

import com.example.MyFirstRestApp.util.PersonNotFoundException;
import com.example.MyFirstRestApp.util.PersonErrorResponce;
import com.example.MyFirstRestApp.models.Person;
import com.example.MyFirstRestApp.services.PeronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeronService peronService;
    @Autowired
    public PeopleController(PeronService peronService) {
        this.peronService = peronService;
    }

    @GetMapping("/getAll")
    public List<Person> getPeople(){
        return peronService.findAllUsers();
    }
    @GetMapping("getId/{id}")
    public Person getOne(@PathVariable("id") int id){
        return peronService.findById(id);
    }
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponce> handleException(PersonNotFoundException ex){
        PersonErrorResponce personErrorResponce = new PersonErrorResponce("Person with this id was is not found!",System.currentTimeMillis());
        return  new ResponseEntity<PersonErrorResponce>(personErrorResponce, HttpStatus.NOT_FOUND);// 404 - status - Error;
    }



}
