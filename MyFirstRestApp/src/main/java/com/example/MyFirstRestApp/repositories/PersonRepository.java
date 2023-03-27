package com.example.MyFirstRestApp.repositories;

import com.example.MyFirstRestApp.models.Friend;
import com.example.MyFirstRestApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String username);
    List<Person> findByStatus(String status);
    Optional<Person> findByLogin(String login);
    Optional<List<Person>> findAllByName(String name);
    void deleteByLogin(String login);
}
