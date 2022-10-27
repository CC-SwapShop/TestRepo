package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProductTest extends TestCase {

    Product product = null;
    Product Emptyproduct = null;
    Product ProductParcel = null;

    @Before
    public void setUp() throws Exception {
        product = new Product("Bed", "Sleep on", "Joburg", "money", "bedimg", "123", "swapped", "Home","");
        Emptyproduct = new Product();

    }

    @Test
    public void setStatusAvailableTest(){
        product.setStatusAvailable();
        assertEquals(product.status, "available");
    }

    @Test
    public void setStatusOfferMadeTest(){
        product.setStatusOfferMade();
        assertEquals(product.status, "Offer Made");
    }

    @Test
    public void setStatusSwappedTest(){
        product.setStatusSwapped();
        assertEquals(product.status, "swapped");
    }

    @Test
    public void checkSwappedTruetest(){
        product.setStatusSwapped();
        assertEquals(product.checkSwapped(), true);
    }

    @Test
    public void checkSwappedFalsetest(){
        product.setStatusAvailable();
        assertEquals(product.checkSwapped(), false);
    }

    @Test
    public void describeContentsTest(){
        int result = product.describeContents();
        assertEquals(result, 0);
    }

    @After
    public void tearDown() throws Exception {
        product = null;
    }
}