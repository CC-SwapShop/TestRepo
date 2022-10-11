package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;
import android.view.View;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class user_home_test {

    @Rule
    public ActivityTestRule<UserMenu> homeActivityTestRule = new ActivityTestRule<UserMenu>(UserMenu.class);

    private UserMenu Umenu = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null ,false);


    @Before
    public void setUp() throws Exception {
        Umenu = homeActivityTestRule.getActivity();
    }

    @Test
    public void SwapShopTextView(){
        View view =  Umenu.findViewById(R.id.textView13);
        assertNotNull(view);

    }

    @Test
    public void testwatchlist(){
        View view = Umenu.findViewById(R.id.btnWatch);
        assertNotNull(view);
    }

    @Test
    public void TextSwapShopTextView(){
        TextView textViewTest = Umenu.findViewById(R.id.textView13);
        String actual = textViewTest.getText().toString();
        String expected = "SWAPSHOP";

        assertEquals(actual,expected);
        Umenu.finish();
    }
    //@Test
   // public void testsearch(){
    //    onView(withId(R.id.search)).perform(click());
   //}

    @Test
    public void testupload(){
        onView(withId(R.id.upload)).perform(click());
    }

    @Test
    public void testswaps(){
        onView(withId(R.id.swaps)).perform(click());
    }

    @Test
    public void testchat(){
        onView(withId(R.id.chat)).perform(click());
    }

}
