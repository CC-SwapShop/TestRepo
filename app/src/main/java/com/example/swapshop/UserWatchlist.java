package com.example.swapshop;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserWatchlist implements Parcelable {

    //Variables for Watch list
    public List<String> ProductIDs;
    public List<Boolean> ProductSwappedViewed;

    //defining constructor for watch list
    public UserWatchlist(){
        ProductIDs = new ArrayList<>();
        ProductSwappedViewed = new ArrayList<>();
    }

    //Products in watch list
    protected UserWatchlist(Parcel in) {
        ProductIDs = in.createStringArrayList();
    }

    //Creating watchlist according to user
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

    //Products in watchlist
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(ProductIDs);
    }
}
