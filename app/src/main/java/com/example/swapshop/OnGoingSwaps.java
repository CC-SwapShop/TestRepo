package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

public class OnGoingSwaps {
    public String customer, provider,productId;
    public boolean ongoing;

    public OnGoingSwaps(String customer,String provider,String productId, boolean ongoing){
        this.customer = customer;
        this.provider = provider;
        this.productId = productId;
        this.ongoing = ongoing;

    }

}
