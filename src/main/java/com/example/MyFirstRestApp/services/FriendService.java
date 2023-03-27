package com.example.MyFirstRestApp.services;

import com.example.MyFirstRestApp.models.Friend;
import com.example.MyFirstRestApp.models.Person;
import com.example.MyFirstRestApp.repositories.FriendRepository;
import com.example.MyFirstRestApp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final PersonRepository personRepository;
    @Autowired
    public FriendService(FriendRepository friendRepository, PersonRepository personRepository) {
        this.friendRepository = friendRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public void addFriend(Person person, Person personFrend){
        Person data = personRepository.findByLogin(person.getLogin()).get();
        for (Friend item:data.getFrends()) {
            if(item.getLogin().equals(personFrend.getLogin()))return;
        }
        Friend frend = new Friend(personFrend.getLogin(), person);
        data.getFrends().add(frend);
        friendRepository.save(frend);
    }
    @Transactional
    public void delete(String login){friendRepository.deleteByLogin(login);}
    public List<Friend> getAllFriends(Person person){ return friendRepository.findAllByPerson(person).get();}
}
