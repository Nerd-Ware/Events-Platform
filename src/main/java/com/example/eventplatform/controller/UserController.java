package com.example.eventplatform.controller;

import com.example.eventplatform.model.DbUser;
import com.example.eventplatform.repository.DbUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    @Autowired
    DbUserRepository dbUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home(){
        return "home.html";
    }

    @GetMapping("/login")
    public String login(){

        return "login.html";
    }


    @GetMapping("/signup")
    public String signuppage(){

        return "signup.html";
    }


    @PostMapping("/signup")
    public RedirectView signup(@ModelAttribute DbUser user){
        DbUser newUser = new DbUser(user.getUsername(),bCryptPasswordEncoder.encode(user.getPassword()) , user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getBio(),user.getImg());

        dbUserRepository.save(newUser);
        return new RedirectView("/login");
    }
}
