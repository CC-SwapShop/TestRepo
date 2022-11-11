package com.example.swapshop;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Instrumentation;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatTest extends TestCase {

    @Rule
    public ActivityTestRule<ChatActivity> ChatActivityTestRule = new ActivityTestRule<>(ChatActivity.class);

    private ChatActivity chat = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(),null ,false);

    public static final String STRING_TO_BE_TYPED_ProdDesc = "drives";
    public static final String STRING_TO_BE_TYPED_ProdName = "car";
    public static final String STRING_TO_BE_TYPED_Message = "sup";
    public static final String STRING_TO_BE_TYPED_MSend = "sup";
    public static final String STRING_TO_BE_TYPED_Accept = "accept";
    public static final String STRING_TO_BE_TYPED_Decline = "decline";

    @Before
    public void setUp() throws Exception {
        chat = ChatActivityTestRule.getActivity();
    }


    @Test
    public void testTextView(){
        TextView textView = chat.findViewById(R.id.txtMProdDesc1);
        assertNotNull(textView);
    }



    @After
    public void tearDown() throws Exception {
        chat = null;
    }
}