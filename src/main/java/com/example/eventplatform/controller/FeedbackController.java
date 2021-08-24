package com.example.eventplatform.controller;

import com.example.eventplatform.model.DbUser;
import com.example.eventplatform.model.Feedback;
import com.example.eventplatform.repository.DbUserRepository;
import com.example.eventplatform.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class FeedbackController {


    @Autowired
    DbUserRepository dbUserRepository;

    @Autowired
    FeedbackRepository feedbackRepository;


    @GetMapping("/aboutus")
    public String test(Model model){
        List<Feedback> allFeedbacks= (List<Feedback>) feedbackRepository.findAll();
        model.addAttribute("allFeedbacks",allFeedbacks);
        return "aboutus.html";
    }


    @PostMapping("/aboutus")
    public RedirectView addFeedback(Principal principal,
                                    @RequestParam (value = "email") String email ,
                                    @RequestParam (value = "body") String body ){
        Feedback feedback= new Feedback(principal.getName(),email , body );
        feedback.setUsername(principal.getName());
        feedback.setBody(body);
        feedback.setEmail(email);
        feedbackRepository.save(feedback);
        return new RedirectView("/aboutus");
    }



    @GetMapping("/aboutus/{username}")
    public String addFeedback(Principal p,Model model ,@PathVariable("username") String username) {
        DbUser user = dbUserRepository.findByUsername(username);
        model.addAttribute("user", user);
        DbUser me = dbUserRepository.findByUsername(p.getName());

        if (dbUserRepository.findByUsername(p.getName()).getFollowing().contains(user) || user == me) {
            model.addAttribute("status", false);
            if (user != me) {
                model.addAttribute("statusUnfollow", true);
            }
        } else {
            model.addAttribute("status", true);
            model.addAttribute("statusUnfollow", false);
        }

        return "user.html";
    }

}
