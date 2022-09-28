package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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
public class ChatTest {
    @Rule
    public ActivityTestRule<Chat> chatActivityTestRule = new ActivityTestRule<>(Chat.class);

    private Chat chat = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null ,false);

    @Before
    public void setUp() throws Exception {
        chat = chatActivityTestRule.getActivity();
    }

    @Test
    public void ChatTextTest() {
        View view = chat.findViewById(R.id.txtChatHeading);
        assertNotNull(view);
    }

    @Test
    public void registerTextTest2(){
        TextView textViewTest = chat.findViewById(R.id.txtChatHeading);
        String actual = textViewTest.getText().toString();
        String expected = "SWAPSHOP";

        assertEquals(actual,expected);
        chat.finish();
    }

}
