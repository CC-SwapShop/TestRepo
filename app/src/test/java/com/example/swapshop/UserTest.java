package com.example.swapshop;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

public class UserTest extends TestCase {

    User user = new User("Jared", "jared@gmail.com");

            @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
    }

}