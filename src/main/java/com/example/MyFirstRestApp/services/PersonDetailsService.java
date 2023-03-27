package com.example.MyFirstRestApp.services;

import com.example.MyFirstRestApp.models.Person;
import com.example.MyFirstRestApp.repositories.PersonRepository;
import com.example.MyFirstRestApp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;
    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByLogin(login);
        if(person.isEmpty()) throw new UsernameNotFoundException("User not found");
        person.get().setStatus("AUTHORIZED");
        personRepository.save(person.get());
        return new PersonDetails(person.get());
    }
}
