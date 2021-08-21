package com.example.eventplatform.model;
import javax.persistence.*;

@Entity
public class EventNeeds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int count;
    @ManyToOne()
    Event event;





    public EventNeeds(){

    }
    public EventNeeds(String name, int count) {
        this.name = name;
        this.count = count;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
