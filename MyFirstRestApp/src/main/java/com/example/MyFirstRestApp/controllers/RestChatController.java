package com.example.MyFirstRestApp.controllers;

import com.example.MyFirstRestApp.models.Friend;
import com.example.MyFirstRestApp.models.MessageLogin;
import com.example.MyFirstRestApp.models.Person;
import com.example.MyFirstRestApp.security.PersonDetails;
import com.example.MyFirstRestApp.services.FriendService;
import com.example.MyFirstRestApp.services.PeronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class RestChatController {
    private final PeronService peronService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final FriendService friendService;

    @Autowired
    public RestChatController(PeronService peronService, SimpMessagingTemplate simpMessagingTemplate, FriendService friendService) {
        this.peronService = peronService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.friendService = friendService;
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> featchAll() {
        List<Person> users = peronService.findAllAuthorized();
        Set<String> res = users.stream().map(Person::getName).collect(Collectors.toSet());
        return res;
    }
    @GetMapping("/getAllFriends")
    public Set<String> getAllFriends(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = ((PersonDetails) authentication.getPrincipal()).getPerson();
        List<Friend> friends = peronService.findByLogin(person.getLogin()).getFrends();
        for (Friend friend:friends){
            if(peronService.findByLogin(friend.getLogin()) == null){
                friendService.delete(friend.getLogin());
                friends.remove(friend);
            }
        }
        Set<String> res = friends.stream().map(Friend::getLogin).collect(Collectors.toSet());
        return  res;
    }

    @GetMapping("/getUser")
    public String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = ((PersonDetails) authentication.getPrincipal()).getPerson();
        return person.getName();
    }
    @GetMapping("/getStatus")
    public String getStatus(@RequestParam("login") String login) {
        return peronService.findByLogin(login).getStatus();
    }
    @GetMapping("/getStatusget")
    public String getStatusget(@RequestParam("login") String login) {
        return peronService.findByName(login).getStatus();
    }
    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageLogin messageLogin) {
        System.out.println("message" + messageLogin + "to" + to);
        if (peronService.findByLogin(to) != null) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, messageLogin);
        }
    }
}
