package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    public String name, description, location,reqProduct, img, UID;
    public Boolean swapped;

    public Product(){

    }

    //View of product
    public Product(String name,String description, String location,String reqProduct, String img, String UID,Boolean swapped){
        this.name = name;
        this.description = description;
        this.location = location;
        this.img = img;
        this.UID = UID;
        this.swapped = swapped;
        this.reqProduct = reqProduct;
    }

    protected Product(Parcel in) {
        name = in.readString();
        description = in.readString();
        location = in.readString();
        reqProduct = in.readString();
        img = in.readString();
        UID = in.readString();
        byte tmpSwapped = in.readByte();
        swapped = tmpSwapped == 0 ? null : tmpSwapped == 1;
    }

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
        parcel.writeByte((byte) (swapped == null ? 0 : swapped ? 1 : 2));
    }

    public void setSwapped(Boolean swapped){
        this.swapped = swapped;
    }
}
