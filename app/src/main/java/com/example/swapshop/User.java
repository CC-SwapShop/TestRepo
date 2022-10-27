package com.example.swapshop;

public class User {

    //Variables to use
    public String name, email, img;
    public int totalrating, rcount;
    public long rating;


    //Empty constructor required
    public User(){

    }

    //Populated constructor
    public User(String name, String email, String img, long rating, int rcount, int totalrating){
        this.name = name;
        this.email = email;
        this.img = img;
        this.rating = rating;
        this.rcount = rcount;
        this.totalrating = totalrating;
    }
}