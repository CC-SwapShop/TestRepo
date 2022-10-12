package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    //Defining variables for product class
    public String name, description, location,reqProduct, img, UID, status, category;

    //Required empty constructor
    public Product(){

    }

    //View of product
    //Attribute constructor
    public Product(String name,String description, String location,String reqProduct, String img, String UID,String status, String category){
        this.name = name;
        this.description = description;
        this.location = location;
        this.img = img;
        this.UID = UID;
        this.reqProduct = reqProduct;
        this.status = status;
        this.category = category;
    }


    //Setting status to Available on the product
    public void setStatusAvailable(){
        this.status = "available";
    }

    //Setting status to OfferMade on the product
    public void setStatusOfferMade(){
        this.status = "Offer Made";
    }

    //Setting status to swapped on the product
    public void setStatusSwapped(){
        this.status = "swapped";
    }

    public boolean checkSwapped(){
        if(status.equals("swapped")){
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeString(reqProduct);
        parcel.writeString(img);
        parcel.writeString(UID);
        parcel.writeString(status);
        parcel.writeString(category);
    }
}
