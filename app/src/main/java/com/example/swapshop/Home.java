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
        Bundle bundle = new Bundle();
        bundle.putString("sCategory","Toys");

        Fragment fragment;
        fragment = new SearchEveryone();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,fragment);
        fragmentTransaction.commit();
        //replaceFragment(new SearchEveryone());

        //binding bottom navigation menu for unregistered menu
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                //Search fragment for unregistered menu
                case R.id.search:
                    //replaceFragment(new SearchEveryone());
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("sCategory","Toys");

                    Fragment fragment2;
                    fragment2 = new SearchEveryone();
                    fragment2.setArguments(bundle2);
                    FragmentManager fragmentManager2= getSupportFragmentManager();
                    FragmentTransaction fragmentTransactio2n= fragmentManager2.beginTransaction();
                    fragmentTransactio2n.replace(R.id.flFragment,fragment2);
                    fragmentTransactio2n.commit();
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
}