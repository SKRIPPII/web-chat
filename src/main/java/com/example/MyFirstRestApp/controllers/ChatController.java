package com.example.MyFirstRestApp.controllers;

import com.example.MyFirstRestApp.models.MessageLogin;
import com.example.MyFirstRestApp.models.Person;
import com.example.MyFirstRestApp.security.PersonDetails;
import com.example.MyFirstRestApp.services.PeronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
public class ChatController {
    private final PeronService peronService;

    @Autowired
    public ChatController(PeronService peronService, SimpMessagingTemplate simpMessagingTemplate) {
        this.peronService = peronService;
    }

    @GetMapping("/connect")
    public String connect(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = ((PersonDetails) authentication.getPrincipal()).getPerson();
        model.addAttribute("person",person);
        return "frontend/message";}


}
