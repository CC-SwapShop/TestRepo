package com.example.swapshop;

import android.text.TextUtils;
import android.util.Patterns;

public class Validation {

    //Validation class
    public static boolean StringEmpty(String sLine){
        return sLine.isEmpty();
    }


    //Minimum length for password
    public static boolean minimumPassLength(String sLine){

        return (sLine.length() >= 6);

    }
}
