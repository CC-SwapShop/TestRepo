package com.example.swapshop;

import android.text.TextUtils;
import android.util.Patterns;

public class Validation {

    public static boolean StringEmpty(String sLine){
        return sLine.isEmpty();
    }

    public static boolean matchesEmailPattern(String test){

        return (Patterns.EMAIL_ADDRESS.matcher(test).matches());

    }

    public static boolean minimumPassLength(String sLine){

        return (sLine.length() >= 6);

    }
}
