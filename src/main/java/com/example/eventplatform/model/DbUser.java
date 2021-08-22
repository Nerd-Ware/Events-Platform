package com.example.eventplatform.model;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class DbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String bio;
    private String img;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "following_followers",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "followers_id"))
    List<DbUser> following=new ArrayList<>();

    @ManyToMany(mappedBy = "following")
    List<DbUser> followers=new ArrayList<>();


    @OneToMany(mappedBy = "user")
    List<Event> myEvents = new ArrayList<>();

    @ManyToMany(mappedBy = "attendance")
    List<Event> attendedTo=new ArrayList<>();



    public DbUser(){

    }

    public DbUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio, String img) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.img = img;
    }

    public List<Event> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(List<Event> myEvents) {
        this.myEvents = myEvents;
    }

    public List<DbUser> getFollowing() {
        return following;
    }

    public void setFollowing(List<DbUser> following) {
        this.following = following;
    }

    public List<DbUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<DbUser> followers) {
        this.followers = followers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Event> getAttendedTo() {
        return attendedTo;
    }

    public void setAttendedTo(List<Event> attendedTo) {
        this.attendedTo = attendedTo;

    }
}
