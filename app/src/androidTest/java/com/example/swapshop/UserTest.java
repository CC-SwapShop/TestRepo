package com.example.swapshop;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserTest extends TestCase {

    public String name="Jared", email="jaredmccallum@gmail.com", img="img_url";
    public long rating=5;
    public int rcount=10, totalrating=50;

    private User user = null;
    private User userEmpty = null;

    @Before
    public void setUp() throws Exception {
        user = new User(name, email, img, rating, rcount, totalrating);
        userEmpty = new User();
    }

    @Test
    public void TestUserName() throws Exception{
        assertEquals(user.name, "Jared");
    }

    @Test
    public void TestUserEmail() throws Exception{
        assertEquals(user.email, "jaredmccallum@gmail.com");
    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }
}