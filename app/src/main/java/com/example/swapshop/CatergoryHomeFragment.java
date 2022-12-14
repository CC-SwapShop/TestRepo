package com.example.swapshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CatergoryHomeFragment extends Fragment {
    Button btnHome,btnToys,btnGames,btnSport;

    public CatergoryHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_catergory_home, container, false);

        //variables
        btnHome = view.findViewById(R.id.btnHomeAplliances);
        btnToys = view.findViewById(R.id.btnToys);
        btnGames = view.findViewById(R.id.btnGames);
        btnSport = view.findViewById(R.id.btnSport);

        //Home and appliances
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //changeview
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Home & Appliance");

                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });

        //Toys
        btnToys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //changeview
                Bundle bundle = new Bundle();
                //Sending string to next class
                bundle.putString("sCategory","Toys");
                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });

        //Games
        btnGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //changeview
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Games");
                //Sending string to next class
                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });

        //Sport
        btnSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //changeview
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Sport");

                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });

        //returning views
        return view;
    }
}