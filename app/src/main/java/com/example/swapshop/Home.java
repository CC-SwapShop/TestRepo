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

        binding = com.example.swapshop.databinding.ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new SearchEveryone());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                case R.id.search:
                    replaceFragment(new SearchEveryone());
                    break;
                case R.id.logIn:
                    replaceFragment(new LoginFrag());
                    break;
            }
            return true;

        });
    }
    private void replaceFragment(Fragment fragment)
    {

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,fragment);
        fragmentTransaction.commit();
    }

    public static String TestUnit(String Name){
        return Name;
    }
}