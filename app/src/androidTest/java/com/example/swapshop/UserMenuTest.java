package com.example.swapshop;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;
import android.view.View;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserMenuTest {

    @Rule
    public ActivityTestRule<UserMenu> userMenuActivityTestRule = new ActivityTestRule<UserMenu>(UserMenu.class);

    private UserMenu userMenu = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null ,false);

    @Before
    public void setUp() throws Exception {
        userMenu = userMenuActivityTestRule.getActivity();
    }

    @After
    public void tearDown(){
        userMenu = null;

    }

    @Test
    public void SwapShopTextView(){
        View view =  userMenu.findViewById(R.id.textView13);
        assertNotNull(view);

    }

    @Test
    public void TextSwapShopTextView(){
        TextView textViewTest = userMenu.findViewById(R.id.textView13);
        String actual = textViewTest.getText().toString();
        String expected = "SWAPSHOP";

        assertEquals(actual,expected);
        userMenu.finish();
    }

    @Test
    public void testSearch(){
        onView(withId(R.id.search)).perform(click());
        getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testUpload(){
        onView(withId(R.id.upload)).perform(click());
        getInstrumentation().waitForIdleSync();
    }

    @Test
    public void testChat(){
        onView(withId(R.id.chat)).perform(click());
        getInstrumentation().waitForIdleSync();
    }




}
