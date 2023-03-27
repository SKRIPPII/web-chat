package com.example.MyFirstRestApp.services;

import com.example.MyFirstRestApp.models.Friend;
import com.example.MyFirstRestApp.util.PersonNotFoundException;
import com.example.MyFirstRestApp.models.Person;
import com.example.MyFirstRestApp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeronService {
    private final PersonRepository personRepository;
    @Autowired
    public PeronService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAllUsers(){
        return personRepository.findAll();
    }
    public Person findById(int id){
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }
    public Person findByName(String name){return personRepository.findByName(name).orElse(null);}
    public List<Person> findAllAuthorized(){return personRepository.findByStatus("AUTHORIZED");}
    public List<Person> getAllUsersByName(String name) {
        return personRepository.findAllByName(name).get();
    }
    public Person findByLogin(String login){ return  personRepository.findByLogin(login).get();}
    public void delete(String login){personRepository.deleteByLogin(login);}
}
