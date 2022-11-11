package com.example.swapshop;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserWatchlist_Test extends TestCase {

    //Instantiate ProductID as UserWatchlist object and set to null
    UserWatchlist ProductID = null;

    //Before testing, assign new object and add products
    @Before
    public void setUp() throws Exception {
        ProductID = new UserWatchlist();
        ProductID.ProductIDs.add("aeqzWsxrdyctfvyg");
        ProductID.ProductIDs.add("456yujnhbgbbbbjv");
    }

    @Test
    public void A_Arraytest() {
        String test = ProductID.ProductIDs.get(0);
        assertNotNull(test);
    }

    @Test
    public void B_Arraytexttest() {
        String test = ProductID.ProductIDs.get(0);
        String test2 = "aeqzWsxrdyctfvyg";
        assertEquals(test,test2);
    }

    @Test
    public void C_Arraytest2() {
        String test = ProductID.ProductIDs.get(1);
        assertNotNull(test);
    }

    @Test
    public void D_Arraytexttest2() {
        String test = ProductID.ProductIDs.get(1);
        String test2 = "456yujnhbgbbbbjv";
        assertEquals(test,test2);
    }

    @Test
    public void E_testParcable(){
        UserWatchlist objProduct;
        Intent intent = new Intent();
        intent.putExtra("Curr_Product", ProductID);
        objProduct = intent.getParcelableExtra("Curr_Product");
        assertNotNull(objProduct);
    }


    @Test
    public void F_testContents() {
        assertEquals(ProductID.describeContents(), 0);
    }

    @After
    public void tearDown() throws Exception {
        ProductID = null;
    }
}
