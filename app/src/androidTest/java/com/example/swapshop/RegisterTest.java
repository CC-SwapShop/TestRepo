package com.example.swapshop;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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

    //public void isActivityInView(){
    //    onView(withId(R.id.register)).check(matches(isDisplayed()));
    //}

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

    @After
    public void tearDown() throws Exception {
        register = null;
    }
}