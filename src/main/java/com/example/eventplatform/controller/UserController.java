package com.example.eventplatform.controller;

import com.example.eventplatform.model.DbUser;
import com.example.eventplatform.repository.DbUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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


































    String searchedUser = null;
    @GetMapping("/findfriends")
    public String findFriends( Model model, Principal p) {
        if (searchedUser == null || searchedUser.equals("")){
            List<DbUser> allUsers= (List<DbUser>)dbUserRepository.findAll();
            DbUser me=dbUserRepository.findByUsername(p.getName());
            allUsers.remove(me);
            for (int i = 0; i < me.getFollowing().size(); i++) {
                allUsers.remove(me.getFollowing().get(i));
            }
            model.addAttribute("allUsers",allUsers);
        }else{
            DbUser searched= dbUserRepository.findByUsername(searchedUser);
            if (searched != null){
                List<DbUser> searchedList =new ArrayList<>();
                searchedList.add(searched);
                model.addAttribute("allUsers",searchedList);
                searchedUser = null;
            }
        }
        return "findfriends.html";
    }

    @PostMapping("/searchuser")
    public RedirectView searchUser(@RequestParam(value = "search") String username){
        searchedUser = username;

        return new RedirectView("/findfriends");
    }


    @PostMapping("/addfriend/{id}")
    public RedirectView addFriend(@PathVariable("id") int id ,Principal p){
        DbUser me=dbUserRepository.findByUsername(p.getName());
        DbUser user=dbUserRepository.findById(id).get();
        me.getFollowing().add(user);
        user.getFollowers().add(me);
        dbUserRepository.save(me);
        dbUserRepository.save(user);
        return new RedirectView("/findfriends");
    }
}
