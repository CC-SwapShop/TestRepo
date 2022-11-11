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
public class UploadProductTest extends TestCase {

    @Rule
    public ActivityTestRule<UploadProduct> uploadActivityTestRule = new ActivityTestRule<UploadProduct>(UploadProduct.class);

    UploadProduct uploadProduct = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(),null ,false);

    public static final String STRING_TO_BE_TYPED_PRODUCT = "Keyboard";

    public static final String STRING_TO_BE_TYPED_DESCRIPTION = "type type yes";

    public static final String STRING_TO_BE_TYPED_LOCATION = "your moms house";

    public static final String STRING_TO_BE_TYPED_REQ_PRODUCT = "your mom";

    @Before
    public void setUp() throws Exception {
        uploadProduct = uploadActivityTestRule.getActivity();
    }

    @Test
    public void isActivityInView(){
        onView(withId(R.id.upload_product)).check(matches(isDisplayed()));
    }

    @Test
    public void Testimgadd(){
        onView(withId(R.id.edtAName)).perform(typeText(STRING_TO_BE_TYPED_PRODUCT), closeSoftKeyboard());
        onView(withId(R.id.edtADescription)).perform(typeText(STRING_TO_BE_TYPED_DESCRIPTION), closeSoftKeyboard());
        onView(withId(R.id.edtALocation)).perform(typeText(STRING_TO_BE_TYPED_LOCATION), closeSoftKeyboard());
        onView(withId(R.id.edtALreqProduct)).perform(typeText(STRING_TO_BE_TYPED_REQ_PRODUCT), closeSoftKeyboard());
        onView(withId(R.id.btnAUpload)).perform(click());
    }

    @Test
    public void ProductTextView(){
        View view = uploadProduct.findViewById(R.id.edtAName);
        assertNotNull(view);
    }

    @Test
    public void DescriptionTextView(){
        View view = uploadProduct.findViewById(R.id.edtADescription);
        assertNotNull(view);
    }

    @Test
    public void LocationTextView(){
        View view = uploadProduct.findViewById(R.id.edtALocation);
        assertNotNull(view);
    }

    @Test
    public void ReqProductTextView(){
        View view = uploadProduct.findViewById(R.id.edtALreqProduct);
        assertNotNull(view);
    }

    @Test
    public void testLaunchItems(){
        View fullName = uploadProduct.findViewById(R.id.edtAName);
        View email = uploadProduct.findViewById(R.id.edtADescription);
        View password = uploadProduct.findViewById(R.id.edtALocation);
        View reqProduct = uploadProduct.findViewById(R.id.edtALreqProduct);
        View btnRegister = uploadProduct.findViewById(R.id.btnAUpload);

        assertNotNull(fullName);
        assertNotNull(email);
        assertNotNull(password);
        assertNotNull(reqProduct);
        assertNotNull(btnRegister);

        uploadProduct.finish();
    }

    @Test
    public void testUploadButton(){
        View view = uploadProduct.findViewById(R.id.btnAUpload);
        assertNotNull(view);
    }


    @Test
    public void testUploadButton5(){
        TextView textViewTest = uploadProduct.findViewById(R.id.btnAUpload);
        String actual = textViewTest.getText().toString();
        String expected = "Upload";

        assertEquals(actual,expected);
        uploadProduct.finish();
    }

    @After
    public void tearDown() throws Exception {
        uploadProduct = null;
    }

}