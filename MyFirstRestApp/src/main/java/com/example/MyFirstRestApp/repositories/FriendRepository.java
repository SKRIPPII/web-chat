package com.example.MyFirstRestApp.repositories;

import com.example.MyFirstRestApp.models.Friend;
import com.example.MyFirstRestApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {
    Optional<List<Friend>> findAllByPerson(Person person);
    void deleteByLogin(String login);
}
