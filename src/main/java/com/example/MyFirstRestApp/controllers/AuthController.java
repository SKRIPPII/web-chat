package com.example.MyFirstRestApp.controllers;


import com.example.MyFirstRestApp.models.Person;
import com.example.MyFirstRestApp.repositories.PersonRepository;
import com.example.MyFirstRestApp.security.PersonDetails;
import com.example.MyFirstRestApp.services.FriendService;
import com.example.MyFirstRestApp.services.PeronService;
import com.example.MyFirstRestApp.services.RegistrationService;
import com.example.MyFirstRestApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final PersonRepository personRepository;
    private final FriendService friendService;
    private final PeronService peronService;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, PersonRepository personRepository, FriendService friendService, PeronService peronService) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.personRepository = personRepository;
        this.friendService = friendService;
        this.peronService = peronService;
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login  = ((PersonDetails)authentication.getPrincipal()).getPerson().getLogin();
        Person result = personRepository.findByLogin(login).get();
        result.setStatus("NOT_AUTHORIZED");
        personRepository.save(result);
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        return "redirect:/auth/login";
    }
    @GetMapping("/login")
    public String login() {
        return "registration/login";
    }

    @GetMapping("/reg")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "registration/registration";
    }

    @PostMapping("/reg")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult result) {
        personValidator.validate(person, result);
        if (result.hasErrors()) {
            return "registration/registration";
        }
        registrationService.register(person);
        return "redirect:/auth/login";
    }
    /*
    @GetMapping("/showUsers")
    public String show(Model model) {
        model.addAttribute("people", peronService.findAllUsers());
        return "/info/showUsers";
    }

     */

    @GetMapping("/search")
    public String search(@ModelAttribute("person") Person person) {
        return "/info/search";
    }

    @GetMapping("/search/users")
    public String findUsersByName(@ModelAttribute("person") Person person,@ModelAttribute("friend") Person friend, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person authPerson = ((PersonDetails) authentication.getPrincipal()).getPerson();
        List<Person> users = peronService.getAllUsersByName(person.getName()).stream().filter(person1 -> !(person1.getLogin().equals(authPerson.getLogin()))).toList();
        model.addAttribute("people", users);
        System.out.println(person.getName());
        return "/info/showUsers";
    }
    @PostMapping("/add/{login}")
    public String addFriend(@PathVariable("login")String login){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person authPerson = ((PersonDetails) authentication.getPrincipal()).getPerson();
        Person person = peronService.findByLogin(login);
        friendService.addFriend(authPerson,person);
        return "redirect:/api/hello";
    }
    @GetMapping("/showFriends")
    public String ShowFriends(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person authPerson = ((PersonDetails) authentication.getPrincipal()).getPerson();
        Person data = peronService.findByLogin(authPerson.getLogin());
        model.addAttribute("friends",data.getFrends());
      //  return "/info/showFriends";
        return "/info/friends";
    }
}
