package com.example.swapshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class UserCatergoryFragment extends Fragment {
    Button btnHome1,btnToys1,btnGames1,btnSport1;

    //NOT IN USE
    public UserCatergoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_catergory, container, false);

        btnHome1 = view.findViewById(R.id.btnHomeAplliances1);
        btnToys1 = view.findViewById(R.id.btnToys1);
        btnGames1 = view.findViewById(R.id.btnGames1);
        btnSport1 = view.findViewById(R.id.btnSport1);

        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Home & Appliance");

                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });

        btnToys1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Toys");

                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });

        btnGames1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Games");

                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });

        btnSport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Sport");

                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });

        return view;
    }
}