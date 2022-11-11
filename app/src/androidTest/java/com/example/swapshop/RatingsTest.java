package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Run tests in ascending order
public class RatingsTest extends TestCase {

    //Set Activity to Ratings class
    @Rule
    public ActivityTestRule<Ratings> RatingsActivityTestRule = new ActivityTestRule<>(Ratings.class);

    //Set new ratings object to null
    private Ratings ratings = null;

    //On seting up the test set the ratings object to the ratings activity
    @Before
    public void setUp() throws Exception {
        ratings = RatingsActivityTestRule.getActivity();
    }

    //Test to see whether the ratings activitys layout is the current layout being presented
    @Test
    public void A_isActivityInView(){
        onView(withId(R.id.view_rating)).check(matches(isDisplayed()));
    }

    //Test rating a user 2 stars
    @Test
    public void B_test2Stars(){
        int expected = 2;

        onView(withId(R.id.btn_star2)).perform(click());
        int result = ratings.rating;
        assertEquals(expected, result);
    }

    //Test raing a user 3 stars
    @Test
    public void C_test3Stars(){
        int expected = 3;

        onView(withId(R.id.btn_star3)).perform(click());
        int result = ratings.rating;
        assertEquals(expected, result);
    }

    //Test rating a user 4 stars
    @Test
    public void D_test4Stars(){
        int expected = 4;

        onView(withId(R.id.btn_star4)).perform(click());
        int result = ratings.rating;
        assertEquals(expected, result);
    }

    //Test rating a user 5 stars
    @Test
    public void E_test5Stars(){
        int expected = 5;

        onView(withId(R.id.btn_star5)).perform(click());
        int result = ratings.rating;
        assertEquals(expected, result);
    }

    //Test when you finished to assign new rating to user
    @Test
    public void F_testDone() throws Exception {
        onView(withId(R.id.btnDone)).perform(click());
        setUp();
    }

    //After test is complete set object to null again
    @After
    public void tearDown() throws Exception {
        ratings = null;
    }


}
