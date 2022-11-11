package com.example.swapshop;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HomeTest {

    @Rule
    public ActivityTestRule<Home> homeActivityTestRule = new ActivityTestRule<Home>(Home.class);

    private Home home = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(),null ,false);


    @Before
    public void setUp() throws Exception {
        home = homeActivityTestRule.getActivity();
    }

    @Test
    public void SwapShopTextView(){
        View view =  home.findViewById(R.id.textView);
        assertNotNull(view);
    }

    @Test
    public void testSearch(){
        onView(withId(R.id.search)).perform(click());
    }

    @Test
    public void TextSwapShopTextView(){
        TextView textViewTest = home.findViewById(R.id.textView);
        String actual = textViewTest.getText().toString();
        String expected = "SWAPSHOP";

        assertEquals(actual,expected);
        home.finish();
    }
    @Test
    public void testLogin(){
        onView(withId(R.id.logIn)).perform(click());
    }

}
