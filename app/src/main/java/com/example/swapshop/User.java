package com.example.swapshop;

public class User {

    //Variables to use
    public String name, email, img;
    public int rating, rcount;


    //Empty constructor required
    public User(){

    }

    //Populated constructor
    public User(String name, String email, String img, int rating, int rcount){
        this.name = name;
        this.email = email;
        this.img = img;
        this.rating = rating;
        this.rcount = rcount;
    }
}