package com.example.eventplatform.controller;

import com.example.eventplatform.model.Feedback;
import com.example.eventplatform.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class FeedbackController {



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

}
