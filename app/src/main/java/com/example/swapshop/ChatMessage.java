package com.example.swapshop;

public class ChatMessage {

    //Variables
    public String messageFrom, message, messageTo, chatID;

    //Constructor
    public ChatMessage(String from, String message, String to, String chatID){
        this.messageFrom = from;
        this.message = message;
        this.messageTo = to;
        this.chatID = chatID;
    }
}
