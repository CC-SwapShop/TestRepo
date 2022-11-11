package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import junit.framework.TestCase;

import androidx.test.espresso.matcher.ViewMatchers;
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

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(),null ,false);

    public static final String STRING_TO_BE_TYPED_EMAIL = "bobparker@gmail.com";
    public static final String STRING_TO_BE_TYPED_PASSWORD = "12345678";

    @Before
    public void setUp() throws Exception {
        login_activity = LoginActivityTestRule.getActivity();
    }

    @Test
    public void isActivityInView(){
        onView(withId(R.id.frameLayout4)).check(matches(isDisplayed()));
    }

    @Test
    public void loginTextTest() {
        View view = login_activity.findViewById(R.id.TV_login);
        assertNotNull(view);
    }

    @Test
    public void loginTextTest2(){
        TextView textViewTest = login_activity.findViewById(R.id.TV_login);
        String actual = textViewTest.getText().toString();
        String expected = "Log In";

        assertEquals(actual,expected);
        login_activity.finish();
    }

    @Test
    public void registerTextTest(){
        TextView textViewTest = login_activity.findViewById(R.id.btnLRegister);
        String actual = textViewTest.getText().toString();
        String expected = "Register";

        assertEquals(actual,expected);
        login_activity.finish();
    }

    @Test
    public void registerPageBtnTest() {
        onView(withId(R.id.btnLRegister)).perform(click());
    }

    @Test
    public void testLaunchItems(){
        View email = login_activity.findViewById(R.id.edtLEmail);
        View password = login_activity.findViewById(R.id.edtLPassword);
        assertNotNull(email);
        assertNotNull(password);

        login_activity.finish();
    }

    @Test
    public void testReturnToRegisterPageButton(){
        onView(withId(R.id.btnLRegister)).perform(click());
        Activity register = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNull(register);

        login_activity.finish();
    }

    @Test
    public void testLoginButton(){
        onView(withId(R.id.edtLEmail)).perform(typeText(STRING_TO_BE_TYPED_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.edtLPassword)).perform(typeText(STRING_TO_BE_TYPED_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

//        Activity login = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        onView(withId(R.id.recycler_view2)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//        onView(withId(R.id.text_send)).perform(typeText("unit-testing sucks"), closeSoftKeyboard());
//        onView(withId(R.id.btn_send)).perform(click());
//        onView(withId(R.id.calls)).perform(click());
//        assertNotNull(login);
        login_activity.finish();
    }


    @Test
    public void testLoginButton3(){
        View view = login_activity.findViewById(R.id.btnLogin);
        assertNotNull(view);
    }

    @Test
    public void testLoginButton4(){
        onView(withId(R.id.btnLogin)).perform(click());
        Activity login = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNull(login);

        //login.finish();
    }

    @Test
    public void testLoginButton5(){
        TextView textViewTest = login_activity.findViewById(R.id.btnLogin);
        String actual = textViewTest.getText().toString();
        String expected = "Log in";

        assertEquals(actual,expected);
        login_activity.finish();
    }

    @Test
    public void testNegative1LoginButton(){
        onView(withId(R.id.btnLogin)).perform(click());
        String check = login_activity.unitTest;
        assertEquals(check,"True");
        login_activity.finish();
    }

    @Test
    public void testNegative2LoginButton(){
        onView(withId(R.id.edtLEmail)).perform(typeText(STRING_TO_BE_TYPED_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        String check = login_activity.unitTest;
        assertEquals(check,"True");
        login_activity.finish();
    }

    @Test
    public void testNegative3LoginButton(){
        onView(withId(R.id.edtLPassword)).perform(typeText(STRING_TO_BE_TYPED_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        String check = login_activity.unitTest;
        assertEquals(check,"True");
        login_activity.finish();
    }


    @After
    public void tearDown() throws Exception {
        login_activity = null;
    }
}