package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OnGoingSwaps implements Parcelable {
    public String customer, provider,productId;
    public boolean ongoing;

    public OnGoingSwaps(String customer,String provider,String productId, boolean ongoing){
        this.customer = customer;
        this.provider = provider;
        this.productId = productId;
        this.ongoing = ongoing;

    }

    protected OnGoingSwaps(Parcel in) {
        customer = in.readString();
        provider = in.readString();
        productId = in.readString();
        ongoing = in.readByte() != 0;
    }

    public static final Creator<OnGoingSwaps> CREATOR = new Creator<OnGoingSwaps>() {
        @Override
        public OnGoingSwaps createFromParcel(Parcel in) {
            return new OnGoingSwaps(in);
        }

        @Override
        public OnGoingSwaps[] newArray(int size) {
            return new OnGoingSwaps[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(customer);
        parcel.writeString(provider);
        parcel.writeString(productId);
        parcel.writeByte((byte) (ongoing ? 1 : 0));
    }

    public String getCustomerName(){


        return customer;
    }
}
