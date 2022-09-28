package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.common.base.Verify;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(AndroidJUnit4.class)
public class RegisterTest extends TestCase {

    @Rule
    public ActivityTestRule<Register> registerActivityTestRule = new ActivityTestRule<Register>(Register.class);

    private Register register = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null ,false);

    public static final String STRING_TO_BE_TYPED_FULL_NAME = "Jared McCallum";

    public static final String STRING_TO_BE_TYPED_EMAIL = "jared@gmail.com";

    public static final String STRING_TO_BE_TYPED_PASSWORD = "jared123";

    @Before
    public void setUp() throws Exception {
        register = registerActivityTestRule.getActivity();
    }

    public void isActivityInView(){
        onView(withId(R.id.register)).check(matches(isDisplayed()));
    }

    @Test
    public void SwapShopTextView(){
        View view = register .findViewById(R.id.textView);
        assertNotNull(view);

    }
    @Test
    public void NameTextView(){
        View view = register .findViewById(R.id.textView3);
        assertNotNull(view);

    }

    @Test
    public void EmailTextView(){
        View view = register .findViewById(R.id.textView4);
        assertNotNull(view);

    }

    @Test
    public void PasswordTextView(){
        View view = register .findViewById(R.id.textView5);
        assertNotNull(view);

    }

    @Test
    public void testLaunchItems(){
        View fullName = register.findViewById(R.id.edtRName);
        View email = register.findViewById(R.id.edtREmail);
        View password = register.findViewById(R.id.edtRPassword);
        View btnRegister = register.findViewById(R.id.btnRegister);

        assertNotNull(fullName);
        assertNotNull(email);
        assertNotNull(password);
        assertNotNull(btnRegister);

        register.finish();
    }

    @Test
    public void testRegisterButton(){
        onView(withId(R.id.edtRName)).perform(typeText(STRING_TO_BE_TYPED_FULL_NAME), closeSoftKeyboard());
        onView(withId(R.id.edtREmail)).perform(typeText(STRING_TO_BE_TYPED_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.edtRPassword)).perform(typeText(STRING_TO_BE_TYPED_PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.btnRegister)).perform(click(), closeSoftKeyboard());
        String check = register.unitTest;
        assertEquals(check,"True");
        register.finish();
    }

    /*@Test
    public void testRegisterButton2(){
        onView(withId(R.id.btnRegister)).perform(click());
    }

    @Test
    public void testRegisterButton3(){
        View view = register.findViewById(R.id.btnRegister);
        assertNotNull(view);
    }

    @Test
    public void testRegisterButton4(){
        onView(withId(R.id.btnRegister)).perform(click());
        Activity login = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNull(login);

        //login.finish();
    }

    @Test
    public void testRegisterButton5(){
        TextView textViewTest = register.findViewById(R.id.btnRegister);
        String actual = textViewTest.getText().toString();
        String expected = "Register";

        assertEquals(actual,expected);
        register.finish();
    }

    @Test
    public void testNegative1RegisterButton(){
        onView(withId(R.id.btnRegister)).perform(click());
        String check = register.unitTest;
        assertEquals(check,"True");
        register.finish();
    }

    @Test
    public void testNegative2RegisterButton(){
        onView(withId(R.id.edtRName)).perform(typeText(STRING_TO_BE_TYPED_FULL_NAME), closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click());
        String check = register.unitTest;
        assertEquals(check,"True");
        register.finish();
    }

    @Test
    public void testNegative3RegisterButton(){
        onView(withId(R.id.edtREmail)).perform(typeText(STRING_TO_BE_TYPED_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click());
        String check = register.unitTest;
        assertEquals(check,"True");
        register.finish();
    }

    @Test
    public void testNegative4RegisterButton(){
        onView(withId(R.id.edtRName)).perform(typeText(STRING_TO_BE_TYPED_FULL_NAME), closeSoftKeyboard());
        onView(withId(R.id.edtREmail)).perform(typeText(STRING_TO_BE_TYPED_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click());
        String check = register.unitTest;
        assertEquals(check,"True");
        register.finish();
    }

    @Test
    public void testNegative5RegisterButton(){
        onView(withId(R.id.edtRPassword)).perform(typeText(STRING_TO_BE_TYPED_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click());
        String check = register.unitTest;
        assertEquals(check,"True");
        register.finish();
    }

    @Test
    public void testNegative6RegisterButton(){
        onView(withId(R.id.edtRPassword)).perform(typeText(STRING_TO_BE_TYPED_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click());
        String check = register.unitTest;
        assertEquals(check,"True");
        register.finish();
    }

    @Test
    public void testNegative7RegisterButton(){
        onView(withId(R.id.edtRName)).perform(typeText(STRING_TO_BE_TYPED_FULL_NAME), closeSoftKeyboard());
        onView(withId(R.id.edtREmail)).perform(typeText(STRING_TO_BE_TYPED_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.edtRPassword)).perform(typeText(STRING_TO_BE_TYPED_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click());
        String check = register.unitTest;
        assertEquals(check,"True");
        register.finish();
    }*/

    @After
    public void tearDown() throws Exception {
        register = null;
    }
}