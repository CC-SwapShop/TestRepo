package com.example.swapshop;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AcceptedSwapTest extends TestCase {

    public String customer="me", provider="you",productId="123";

    private AcceptedSwap acceptedSwap = null;

    @Before
    public void setUp() throws Exception {
        acceptedSwap = new AcceptedSwap(customer, provider, productId);
    }

    @Test
    public void TestCustomer() throws Exception{
        assertEquals(acceptedSwap.customer, "me");
    }

    @Test
    public void TestProvider() throws Exception{
        assertEquals(acceptedSwap.provider, "you");
    }

    @Test
    public void TestProductId() throws Exception{
        assertEquals(acceptedSwap.productId, "123");
    }

    @After
    public void tearDown() throws Exception {
        acceptedSwap = null;
    }
}