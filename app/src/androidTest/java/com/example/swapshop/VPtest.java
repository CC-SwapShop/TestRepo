package com.example.swapshop;

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
public class VPtest {

    @Rule
    public ActivityTestRule<ViewProduct> VPActivityTestRule = new ActivityTestRule<ViewProduct>(ViewProduct.class);

    private ViewProduct vp = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);


    @Before
    public void setUp() throws Exception {
        vp = VPActivityTestRule.getActivity();
    }

    @Test
    public void SwapShopTextView() {
        View view = vp.findViewById(R.id.textView14);
        assertNotNull(view);
    }

    @Test
    public void TextSwapShopTextView(){
        TextView textViewTest = vp.findViewById(R.id.textView14);
        String actual = textViewTest.getText().toString();
        String expected = "SWAPSHOP";

        assertEquals(actual,expected);
        vp.finish();
    }

    @Test
    public void DescTextView() {
        View view = vp.findViewById(R.id.textView2);
        assertNotNull(view);
    }

    @Test
    public void TextDescTextView(){
        TextView textViewTest = vp.findViewById(R.id.textView2);
        String actual = textViewTest.getText().toString();
        String expected = "Description";

        assertEquals(actual,expected);
        vp.finish();
    }

    @Test
    public void UserwantTextView() {
        View view = vp.findViewById(R.id.textView6);
        assertNotNull(view);
    }

    @Test
    public void TextUserwantTextView(){
        TextView textViewTest = vp.findViewById(R.id.textView6);
        String actual = textViewTest.getText().toString();
        String expected = "What user wants";

        assertEquals(actual,expected);
        vp.finish();
    }

}