package com.example.swapshop;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidationTest {

    private Validation validation = null;

    @Before
    public void setUp() throws Exception {
        validation = new Validation();
    }

    @Test
    public void A_notEmptyEmailPass() {
        boolean ans = Validation.StringEmpty("test@gmail.com");
        assertEquals(false, ans);
    }

    @Test
    public void B_EmptyEmailPass() {
        boolean ans = Validation.StringEmpty("");
        assertEquals(true, ans);
    }

    @Test
    public void C_minimumPassLengthEqual() {
        boolean ans = Validation.minimumPassLength("123456");
        assertEquals(true, ans);
    }

    @Test
    public void E_minimumPassLengthGreater() {
        boolean ans = Validation.minimumPassLength("1234567");
        assertEquals(true, ans);
    }

    @Test
    public void F_minimumPassLengthLess() {
        boolean ans = Validation.minimumPassLength("12345");
        assertEquals(false, ans);
    }

    @After
    public void tearDown() throws Exception {
        validation = null;
    }
}
