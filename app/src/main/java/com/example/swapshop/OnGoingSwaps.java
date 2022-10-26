package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnGoingSwaps implements Parcelable {

    //Variables
    public String customer, provider,productId;
    public boolean ongoing;
    String sCustomer,sprovider,sproductname;

    //Constructor
    public OnGoingSwaps(String customer,String provider,String productId, boolean ongoing){
        this.customer = customer;
        this.provider = provider;
        this.productId = productId;
        this.ongoing = ongoing;

    }

    //OnGoing swaps values from previous class
    protected OnGoingSwaps(Parcel in) {
        customer = in.readString();
        provider = in.readString();
        productId = in.readString();
        ongoing = in.readByte() != 0;
    }

    //Creator function
    public static final Creator<OnGoingSwaps> CREATOR = new Creator<OnGoingSwaps>() {
        @Override
        public OnGoingSwaps createFromParcel(Parcel in) {
            return new OnGoingSwaps(in);
        }

        //new Array returning size
        @Override
        public OnGoingSwaps[] newArray(int size) {
            return new OnGoingSwaps[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //Writing values to parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(customer);
        parcel.writeString(provider);
        parcel.writeString(productId);
        parcel.writeByte((byte) (ongoing ? 1 : 0));
    }

    //Getting customer name to swap item with owner of item
    public String getCustomerName(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(customer);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    String sKey = postsnapshot.getKey();
                    if(sKey.equals("name")){
                        sCustomer = postsnapshot.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return "Customer: " + sCustomer;
    }

    //Owner of item information
    public String getProviderName(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(provider);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    String sKey = postsnapshot.getKey();
                    if(sKey.equals("name")){
                        sprovider = postsnapshot.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return "Product owner: " +  sprovider;
    }

    //Getting name of item to be swapped
    public String getProductName(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products").child(productId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    String sKey = postsnapshot.getKey();
                    if(sKey.equals("name")){
                        sproductname = postsnapshot.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return "Product name: " +  sproductname;
    }
}
