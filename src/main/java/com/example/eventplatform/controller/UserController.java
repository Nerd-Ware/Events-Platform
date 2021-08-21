package com.example.eventplatform.controller;

import com.example.eventplatform.model.DbUser;
import com.example.eventplatform.repository.DbUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

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

    @GetMapping("/profile")
    public String profilepage(Principal p, Model model) {
        DbUser me=dbUserRepository.findByUsername(p.getName());
        model.addAttribute("me",me);
        return "profile.html";
    }

    @GetMapping("/user/{id}")
    public String profileIdpage( Model model, @PathVariable("id") int id,Principal p) {
        DbUser user=dbUserRepository.findById(id).get();
        model.addAttribute("user",user);
        DbUser me=dbUserRepository.findByUsername(p.getName());

        if (dbUserRepository.findByUsername(p.getName()).getFollowing().contains(user) || user == me){
            model.addAttribute("status",false);
            if(user != me){
                model.addAttribute("statusUnfollow",true);
            }
        }else {
            model.addAttribute("status",true);
            model.addAttribute("statusUnfollow",false);
        }

        return "user.html";
    }

    @PostMapping("/follow/{id}")
    public RedirectView follow(@PathVariable("id") int id ,Principal p){
        DbUser me=dbUserRepository.findByUsername(p.getName());
        DbUser user=dbUserRepository.findById(id).get();
        me.getFollowing().add(user);
        user.getFollowers().add(me);
        dbUserRepository.save(me);
        dbUserRepository.save(user);
        return new RedirectView("/user/{id}");
    }


    @PostMapping("/unfollow/{id}")
    public RedirectView unFollow(@PathVariable("id") int id ,Principal p){
        DbUser me=dbUserRepository.findByUsername(p.getName());
        DbUser user=dbUserRepository.findById(id).get();
        me.getFollowing().remove(user);
        user.getFollowers().remove(me);
        dbUserRepository.save(me);
        dbUserRepository.save(user);
        return new RedirectView("/user/{id}");
    }
}
