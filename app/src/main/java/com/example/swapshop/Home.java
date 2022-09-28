package com.example.swapshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

public class Home extends AppCompatActivity {
    //Defining variables
    Button btnView,btnSwap,btnSearch,btnUpload,btnRecord;
    com.example.swapshop.databinding.ActivityHomeBinding binding;

    //Menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Binding used to bind the Home class with the fragment to inflate the view
        binding = com.example.swapshop.databinding.ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Starting fragment to be opened upon unregistered menu
        replaceFragment(new SearchEveryone());

        //binding bottom navigation menu for unregistered menu
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                //Search fragment for unregistered menu
                case R.id.search:
                    replaceFragment(new SearchEveryone());
                    break;
                //LogIn fragment
                case R.id.logIn:
                    replaceFragment(new LoginFrag());
                    break;
            }
            return true;

        });
    }
    private void replaceFragment(Fragment fragment)
    {

        //Switching between fragments
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,fragment);
        fragmentTransaction.commit();
    }

    public static String TestUnit(String Name){
        return Name;
    }
}