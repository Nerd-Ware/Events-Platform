package com.example.eventplatform.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String date;
    private int maxParticipant;
    private String img;
    private LocalDateTime createdAt;
    private String address;

    @ManyToOne()
    DbUser user;

    @OneToMany(mappedBy = "event")
    List<EventNeeds> eventNeeds = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "attendance_attendedTo",
            joinColumns = @JoinColumn(name = "attendedTo_id"),
            inverseJoinColumns = @JoinColumn(name = "attendance_id"))
    List<DbUser> attendance=new ArrayList<>();


    public Event(){

    }


    public Event(String name, String description, String date, int maxParticipant, String img, String address) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.maxParticipant = maxParticipant;
        this.img = img;
        this.createdAt= LocalDateTime.now();
        this.address = address;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DbUser getUser() {
        return user;
    }

    public void setUser(DbUser user) {
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<EventNeeds> getEventNeeds() {
        return eventNeeds;
    }

    public void setEventNeeds(List<EventNeeds> eventNeeds) {
        this.eventNeeds = eventNeeds;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DbUser> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<DbUser> attendance) {
        this.attendance = attendance;

    }
}
