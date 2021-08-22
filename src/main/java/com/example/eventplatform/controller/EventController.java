package com.example.eventplatform.controller;
import com.example.eventplatform.model.DbUser;
import com.example.eventplatform.model.Event;
import com.example.eventplatform.model.EventNeeds;
import com.example.eventplatform.repository.DbUserRepository;
import com.example.eventplatform.repository.EventNeedsRepository;
import com.example.eventplatform.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    DbUserRepository dbUserRepository;
    @Autowired
    EventNeedsRepository eventNeedsRepository;

    @PostMapping("/addevent")
    public RedirectView addEvent(@RequestParam(value="number_links") int number_links,@RequestParam(value="name") String name, @RequestParam(value="description") String description,@RequestParam(value = "date") String date,@RequestParam(value = "maxParticipant") int maxParticipant ,@RequestParam(value="img") String img,@RequestParam(value="name0", required = false) String name0 ,@RequestParam(value="name1", required = false) String name1,@RequestParam(value="name2", required = false) String name2,@RequestParam(value="name3", required = false) String name3,@RequestParam(value="name4", required = false) String name4,@RequestParam(value="number0", required = false) Integer number0,@RequestParam(value="number1", required = false) Integer number1,@RequestParam(value="number2", required = false) Integer number2,@RequestParam(value="number3", required = false) Integer number3,@RequestParam(value="number4", required = false) Integer number4 ,@RequestParam(value="inputAddress", required = false) String address, Principal p){
        DbUser me = dbUserRepository.findByUsername(p.getName());
        Event newEvent = new Event(name,description,date,maxParticipant,img,address);
        newEvent.setUser(me);
        eventRepository.save(newEvent);
        if (name0 != null){
            EventNeeds newEventNeed=new EventNeeds(name0,number0);
            newEventNeed.setEvent(newEvent);
            eventNeedsRepository.save(newEventNeed);
        }
        if (name1 != null){
            EventNeeds newEventNeed=new EventNeeds(name1,number1);
            newEventNeed.setEvent(newEvent);
            eventNeedsRepository.save(newEventNeed);
        }
        if (name2 != null){
            EventNeeds newEventNeed=new EventNeeds(name2,number2);
            newEventNeed.setEvent(newEvent);
            eventNeedsRepository.save(newEventNeed);
        }
        if (name3 != null){
            EventNeeds newEventNeed=new EventNeeds(name3,number3);
            newEventNeed.setEvent(newEvent);
            eventNeedsRepository.save(newEventNeed);
        }
        if (name4 != null){
            EventNeeds newEventNeed=new EventNeeds(name4,number4);
            newEventNeed.setEvent(newEvent);
            eventNeedsRepository.save(newEventNeed);
        }



        return new RedirectView("/profile");
    }

    @GetMapping("/upcommingevents")
    public String upComingEvents(Model model , Principal p){
        DbUser me = dbUserRepository.findByUsername(p.getName());
        List<DbUser> users = (List<DbUser>) dbUserRepository.findAll();
        users.remove(me);
        users.removeAll(me.getFollowing());
        model.addAttribute("users" ,users );
        model.addAttribute("following" ,me.getFollowing() );
        return "upCommingEvents.html";
    }


    @PostMapping("/deleteevent/{id}")
    public RedirectView attend(@PathVariable("id") int id){

        Event event = eventRepository.findById(id).get();
        List<EventNeeds> eventNeeds= event.getEventNeeds();
        eventNeedsRepository.deleteAll(eventNeeds);
        eventRepository.delete(event);
        return new RedirectView("/profile");
    }


}
