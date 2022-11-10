package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class Search_ActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<Search_Activity> SearchActivityTestRule = new ActivityTestRule<Search_Activity>(Search_Activity.class);

    private Search_Activity search_activity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null ,false);

    @Before
    public void setUp() throws Exception {
        search_activity = SearchActivityTestRule.getActivity();
    }

    @Test
    public void isActivityInView(){
        onView(withId(R.id.frameLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void btnOther1Test() {
        onView(withId(R.id.button11)).perform(click());
    }

    @Test
    public void btnGames1Test() {
        onView(withId(R.id.button4)).perform(click());
    }

    @Test
    public void btnSport1Test() {
        onView(withId(R.id.button5)).perform(click());
    }

    @Test
    public void btnSearchProductTest() {
        onView(withId(R.id.button)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        search_activity = null;
    }
}