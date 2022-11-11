package com.example.swapshop;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ValidationTest {
    @Test
    public void notEmptyEmailPass() {
        boolean ans = Validation.StringEmpty("test@gmail.com");
        assertEquals(false, ans);
    }

    @Test
    public void EmptyEmailPass() {
        boolean ans = Validation.StringEmpty("");
        assertEquals(true, ans);
    }

    @Test
    public void minimumPassLengthEqual() {
        boolean ans = Validation.minimumPassLength("123456");
        assertEquals(true, ans);
    }

    @Test
    public void minimumPassLengthGreater() {
        boolean ans = Validation.minimumPassLength("1234567");
        assertEquals(true, ans);
    }

    @Test
    public void minimumPassLengthLess() {
        boolean ans = Validation.minimumPassLength("12345");
        assertEquals(false, ans);
    }
}
