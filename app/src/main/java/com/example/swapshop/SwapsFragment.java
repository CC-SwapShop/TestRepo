package com.example.swapshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SwapsFragment extends Fragment {

    //empty constructor required for fragment
    public SwapsFragment() {
        // Required empty public constructor
    }

    //onCreateView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swaps, container, false);
    }

}