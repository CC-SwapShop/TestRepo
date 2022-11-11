package com.example.swapshop;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserWatchlist_Test extends TestCase {

    UserWatchlist ProductID = null;


    @Before
    public void setUp() throws Exception {

        ProductID = new UserWatchlist();
        ProductID.ProductIDs.add("aeqzWsxrdyctfvyg");
        ProductID.ProductIDs.add("456yujnhbgbbbbjv");

    }

    @Test
    public void Arraytest() {
        String test = ProductID.ProductIDs.get(0);
        assertNotNull(test);
    }

    @Test
    public void Arraytexttest() {
        String test = ProductID.ProductIDs.get(0);
        String test2 = "aeqzWsxrdyctfvyg";
        assertEquals(test,test2);
    }

    @Test
    public void Arraytest2() {
        String test = ProductID.ProductIDs.get(1);
        assertNotNull(test);
    }

    @Test
    public void Arraytexttest2() {
        String test = ProductID.ProductIDs.get(1);
        String test2 = "456yujnhbgbbbbjv";
        assertEquals(test,test2);
    }

    @Test
    public void testParcable(){
        UserWatchlist objProduct;
        Intent intent = new Intent();
        intent.putExtra("Curr_Product", ProductID);
        objProduct = intent.getParcelableExtra("Curr_Product");
        assertNotNull(objProduct);

    }

}
