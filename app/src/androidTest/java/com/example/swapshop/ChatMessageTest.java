package com.example.swapshop;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatMessageTest extends TestCase {
    String messageFrom="me", message="sup", messageTo="you";

    private ChatMessage chatMessage = null;

    @Before
    public void setUp() throws Exception {
        chatMessage = new ChatMessage(messageFrom, message, messageTo);
    }

    @Test
    public void TestMessageFrom() throws Exception{
        assertEquals(chatMessage.messageFrom, "me");
    }

    @Test
    public void TestMessage() throws Exception{
        assertEquals(chatMessage.message, "sup");
    }

    @Test
    public void TestMessageTo() throws Exception{
        assertEquals(chatMessage.messageTo, "you");
    }

    @Test
    public void tearDown() throws Exception {
        chatMessage = null;
    }
}