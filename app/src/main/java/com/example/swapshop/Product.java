package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    //Defining variables for product class
    public String name, description, location,reqProduct, img, UID, status, category,swappedUID;
    boolean ans;

    //Required empty constructor
    public Product(){

    }

    //View of product
    //Attribute constructor
    public Product(String name,String description, String location,String reqProduct, String img, String UID,String status, String category,String swappedUID){
        this.name = name;
        this.description = description;
        this.location = location;
        this.img = img;
        this.UID = UID;
        this.reqProduct = reqProduct;
        this.status = status;
        this.category = category;
        this.swappedUID = swappedUID;
    }

    //From parcel
    protected Product(Parcel in) {
        name = in.readString();
        description = in.readString();
        location = in.readString();
        reqProduct = in.readString();
        img = in.readString();
        UID = in.readString();
        status = in.readString();
        category = in.readString();
        swappedUID = in.readString();
    }

    //Creating product
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    //Checking if item is swapped
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

    //Writing to parcel
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
        parcel.writeString(swappedUID);
    }
}
