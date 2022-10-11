package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.util.Log;

import junit.framework.TestCase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class Login_ActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<Login_Activity> LoginActivityTestRule = new ActivityTestRule<>(Login_Activity.class);

    private Login_Activity login_activity = null;

    @Before
    public void setUp() throws Exception {
        login_activity = LoginActivityTestRule.getActivity();
    }

    @Test
    public void isActivityInView(){
        onView(withId(R.id.frameLayout4)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        login_activity = null;
    }
}