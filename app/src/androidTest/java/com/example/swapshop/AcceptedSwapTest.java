package com.example.swapshop;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AcceptedSwapTest extends TestCase {

    //Create variables which will be used for testing
    public String customer="Me", provider="You",productId="123";

    //Create acceptedSwap object and set it to null
    private AcceptedSwap acceptedSwap = null;

    //Before the test runs set it to a new accepted swap object with the variables created
    @Before
    public void setUp() throws Exception {
        acceptedSwap = new AcceptedSwap(customer, provider, productId);
    }

    //Test if the swap correctly assigned the customer element to the object
    @Test
    public void TestCustomer() throws Exception{
        assertEquals(acceptedSwap.customer, "Me");
    }

    //Test if the swap correctly assigned the provider element to the object
    @Test
    public void TestProvider() throws Exception{
        assertEquals(acceptedSwap.provider, "You");
    }

    //Test if the swap correctly assigned the product id element to the object
    @Test
    public void TestProductId() throws Exception{
        assertEquals(acceptedSwap.productId, "123");
    }

    //After the test is completed assign null to the swap object again
    @After
    public void tearDown() throws Exception {
        acceptedSwap = null;
    }
}