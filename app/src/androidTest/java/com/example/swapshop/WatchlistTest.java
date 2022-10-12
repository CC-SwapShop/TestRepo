package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WatchlistTest extends TestCase {

    @Rule
    public ActivityTestRule<Watchlist> watchlistActivityTestRule = new ActivityTestRule<Watchlist>(Watchlist.class);

    private Watchlist watchlist = null;

    @Before
    public void setUp() throws Exception {
        watchlist = watchlistActivityTestRule.getActivity();
    }

    @Test
    public void isActivityInView(){
        onView(withId(R.id.layout)).check(matches(isDisplayed()));
    }

    @Test
    public void registerPageBtnTest() {
        onView(withId(R.id.recycler_view1)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        watchlist = null;
    }
}