package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Instrumentation;
import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.swapshop.Home;
import com.example.swapshop.R;
import com.example.swapshop.Update_Activity;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class updatedetailsTest extends TestCase{

    @Rule
    public ActivityTestRule<Update_Activity> updatedetailsActivityTestRule = new ActivityTestRule<Update_Activity>(Update_Activity.class);

    private Update_Activity update_details = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(),null ,false);

    public static final String STRING_TO_BE_TYPED_FULL_NAME = "Jared McCallum";

    public static final String STRING_TO_BE_TYPED_EMAIL = "jared@gmail.com";

    public static final String STRING_TO_BE_TYPED_PASSWORD = "jared123";

    @Before
    public void setUp() throws Exception {
        update_details = updatedetailsActivityTestRule.getActivity();
    }

    @Test
    public void isActivityInView(){
        onView(withId(R.id.updated_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void SwapShopTextView(){
        View view = update_details.findViewById(R.id.textView11);
        assertNotNull(view);

    }
    @Test
    public void NameTextView(){
        View view = update_details.findViewById(R.id.textView12);
        assertNotNull(view);

    }

    @Test
    public void EmailTextView(){
        View view = update_details.findViewById(R.id.textView15);
        assertNotNull(view);

    }

    /*
    @Test
    public void testuploadbutton(){
        onView(withId(R.id.btnuploaddp)).perform(click());
    }
    */

    @Test
    public void testsendemailbutton(){
        onView(withId(R.id.btnsendemail)).perform(click());
    }

    @Test
    public void testchangenamebutton(){
        onView(withId(R.id.btnchguname)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        update_details = null;
    }
}