package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserWatchlist implements Parcelable {
    public List<String> ProductIDs;
    public UserWatchlist(){
        ProductIDs = new ArrayList<>();

    }

    protected UserWatchlist(Parcel in) {
        ProductIDs = in.createStringArrayList();
    }

    public static final Creator<UserWatchlist> CREATOR = new Creator<UserWatchlist>() {
        @Override
        public UserWatchlist createFromParcel(Parcel in) {
            return new UserWatchlist(in);
        }

        @Override
        public UserWatchlist[] newArray(int size) {
            return new UserWatchlist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(ProductIDs);
    }
}
