package com.example.patchme;

public class UserProfile {
    public String firstName, lastName, email, city, state, pickUpAddress;

    public UserProfile(){

    }

    public UserProfile(String firstName, String lastName, String email, String city, String state, String pickUpAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.state = state;
        this.pickUpAddress = pickUpAddress;
    }
}
