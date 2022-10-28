package com.example.swapshop;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Instrumentation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OtherUserProfileTest {
    @Rule
    public ActivityTestRule<OtherUserProfile> otherUserProfileActivityTestRule = new ActivityTestRule<>(OtherUserProfile.class);

    private OtherUserProfile otherUserProfile = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(),null ,false);

    @Before
    public void setUp() throws Exception {
        otherUserProfile = otherUserProfileActivityTestRule.getActivity();
    }

    @Test
    public void AtestNameTextView(){
        TextView txtUIName = otherUserProfile.findViewById(R.id.txtNameOther);
        String actual = txtUIName.getText().toString();
        String expected = "Bob Parker";

        assertEquals(actual,expected);
    }

    @Test
    public void BtestNameTextViewNull(){
        TextView txtUIName = otherUserProfile.findViewById(R.id.txtNameOther);

        assertNotNull(txtUIName);
    }

    @Test
    public void testEmailTextView(){
        TextView txtUIEmail = otherUserProfile.findViewById(R.id.textView9);
        String actual = txtUIEmail.getText().toString();
        String expected = "Email:";


        assertEquals(actual,expected);
    }

    @Test
    public void testEmailNullTextView(){
        TextView txtUIEmail = otherUserProfile.findViewById(R.id.textView9);

        assertNotNull(txtUIEmail);
    }

    @Test
    public void testUserEmailTextView(){
        TextView txtUIEmail = otherUserProfile.findViewById(R.id.txtEmailOther);

        String actual = txtUIEmail.getText().toString();
        String expected = "bobparker@gmail.com";

        assertEquals(actual,expected);
    }

    @Test
    public void testUserEmailNullTextView(){
        TextView txtUIUserEmail = otherUserProfile.findViewById(R.id.txtEmailOther);

        assertNotNull(txtUIUserEmail);
    }

    @Test
    public void testRecycleView(){
        RecyclerView recyclerView = otherUserProfile.findViewById(R.id.recycle_view_OtherUser);
        assertNotNull(recyclerView);
    }

    @Test
    public void testImageView(){
        ImageView imageView = otherUserProfile.findViewById(R.id.profile_image);
        assertNotNull(imageView);
    }
}
