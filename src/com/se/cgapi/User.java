package com.se.cgapi;

public class User {

    public User(){

    }

    public User(String name, String lastname, String username, String pword, String email) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.pword = pword;
        this.email = email;
    }

    private String name;
    private String lastname;
    private String username;
    private String pword;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
