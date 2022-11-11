package com.example.swapshop;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Instrumentation;
import android.widget.ImageButton;
import android.widget.TextView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserInfoTest {

    @Rule
    public ActivityTestRule<UserInfoActivity> userInfoActivityActivityTestRule = new ActivityTestRule<UserInfoActivity>(UserInfoActivity.class);

    private UserInfoActivity userInfoActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(),null ,false);

    @Before
    public void setUp() throws Exception {
        userInfoActivity = userInfoActivityActivityTestRule.getActivity();

    }

    @Test
    public void AtestNameTextView(){
        TextView txtUIName = userInfoActivity.findViewById(R.id.txtUI_name);
        String actual = txtUIName.getText().toString();
        String expected = "Rashay";

        assertEquals(actual,expected);
    }

    @Test
    public void BtestNameTextViewNull(){
        TextView txtUIName = userInfoActivity.findViewById(R.id.txtUI_name);

        assertNotNull(txtUIName);
    }

    @Test
    public void testEmailTextView(){
        TextView txtUIEmail = userInfoActivity.findViewById(R.id.textView10);
        String actual = txtUIEmail.getText().toString();
        String expected = "Email:";


        assertEquals(actual,expected);
    }

    @Test
    public void testEmailNullTextView(){
        TextView txtUIEmail = userInfoActivity.findViewById(R.id.textView10);

        assertNotNull(txtUIEmail);
    }

    @Test
    public void testUserEmailTextView(){
        TextView txtUIEmail = userInfoActivity.findViewById(R.id.txtEmailOther2);

        String actual = txtUIEmail.getText().toString();
        String expected = "test1@gmail.com";

        assertEquals(actual,expected);
    }

    @Test
    public void testUserEmailNullTextView(){
        TextView txtUIUserEmail = userInfoActivity.findViewById(R.id.txtEmailOther2);

        assertNotNull(txtUIUserEmail);
    }

    @Test
    public void testUpdateButton(){
        onView(withId(R.id.btnupdate)).perform(click());
        userInfoActivity.finish();
    }

    @Test
    public void testRecycleView(){
        RecyclerView recyclerView = userInfoActivity.findViewById(R.id.recyclerView_UserInfo);
        assertNotNull(recyclerView);
    }


}
