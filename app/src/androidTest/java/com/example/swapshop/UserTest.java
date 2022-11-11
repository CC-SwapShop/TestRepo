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

    public String name="Jared", email="jaredmccallum@gmail.com",
            img="https://firebasestorage.googleapis.com/v0/b/swapshop-8094c.appspot.com/o/userImage.png?alt=media&token=a48bffe3-668a-486c-9128-91b3f968c257";
    public int totalratings = 5, rcount=1;
    public long rating=5;

    private User user = null;
    private User userEmpty = null;

    @Before
    public void setUp() throws Exception {
        user = new User(name, email,img,rating,rcount,totalratings);
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

    @Test
    public void TestUserImg() throws Exception{
        assertEquals(user.img, "https://firebasestorage.googleapis.com/v0/b/swapshop-8094c.appspot.com/o/userImage.png?alt=media&token=a48bffe3-668a-486c-9128-91b3f968c257");
    }

    @Test
    public void TestUserRating() throws Exception{
        assertEquals(user.rating, 5);
    }

    @Test
    public void TestUserTotalRating() throws Exception{
        assertEquals(user.totalratings, 5);
    }

    @Test
    public void TestUserRCount() throws Exception{
        assertEquals(user.rcount, 1);
    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }
}