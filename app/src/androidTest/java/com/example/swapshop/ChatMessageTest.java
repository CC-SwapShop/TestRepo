package com.example.swapshop;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatMessageTest extends TestCase {
    //Set variables for testing
    String messageFrom="me", message="sup", messageTo="you", chatID="123";

    //Create null object
    private ChatMessage chatMessage = null;

    //Set up object to new ChatMessage object
    @Before
    public void setUp() throws Exception {
        chatMessage = new ChatMessage(messageFrom, message, messageTo, chatID);
    }

    //Test messageFrom element in object
    @Test
    public void TestMessageFrom() throws Exception{
        assertEquals(chatMessage.messageFrom, "me");
    }

    //Test message in object
    @Test
    public void TestMessage() throws Exception{
        assertEquals(chatMessage.message, "sup");
    }

    //Test message to in the object
    @Test
    public void TestMessageTo() throws Exception{
        assertEquals(chatMessage.messageTo, "you");
    }

    //Test chatid in the object
    @Test
    public void TestChatID() throws Exception{
        assertEquals(chatMessage.chatID, "123");
    }

    //After test set object to null
    @Test
    public void tearDown() throws Exception {
        chatMessage = null;
    }
}