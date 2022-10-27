package com.example.swapshop;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OnGoingSwapsTest extends TestCase {
    String customer="me", provider="you",productId="123";
    public boolean ongoing=true;

    OnGoingSwaps onGoingSwaps = null;

    @Before
    public void setUp() throws Exception {
        onGoingSwaps = new OnGoingSwaps(customer, provider, productId, ongoing);
    }

    @Test
    public void TestCustomer() throws Exception{
        assertEquals(onGoingSwaps.customer, "me");
    }

    @Test
    public void Testprovider() throws Exception{
        assertEquals(onGoingSwaps.provider, "you");
    }

    @Test
    public void TestproductId() throws Exception{
        assertEquals(onGoingSwaps.productId, "123");
    }

    @Test
    public void Testongoing() throws Exception{
        assertEquals(onGoingSwaps.ongoing, true);
    }

    @After
    public void tearDown() throws Exception {
        onGoingSwaps = null;
    }
}