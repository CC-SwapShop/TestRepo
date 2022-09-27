package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserMenu extends AppCompatActivity {

    //Variables for User Menu class
    ImageButton btnWatch;
    DatabaseReference wReference;
    List<String> productIDs;
    UserWatchlist objWatchlist;

    //binding needed for fragment
    com.example.swapshop.databinding.ActivityUserMenuBinding binding;

    //OnCreate method for UserMenu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To use activity user menu xml
        setContentView(R.layout.activity_user_menu);

        //Binding for fragment
        binding = com.example.swapshop.databinding.ActivityUserMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Search());

        //Switching fragments for user
        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                case R.id.search:
                    replaceFragment(new Search());
                    break;
                case R.id.upload:
                    replaceFragment(new UploadFrag());
                    break;
                case R.id.swaps:
                    replaceFragment(new SwapsFragment());
                    break;
                case R.id.chat:
                    replaceFragment(new ChatFragment());
                    break;
            }
            return true;

        });

        //Watchlist to appear on all fragments
        objWatchlist = new UserWatchlist();

        //Array list
        productIDs = new ArrayList<>();

        //Getting references
        wReference = FirebaseDatabase.getInstance().getReference().child("Watchlist")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Event listener
        wReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productIDs.clear();
                objWatchlist.ProductIDs.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    //String sKey = postsnapshot.getKey();
                    productIDs.add(postsnapshot.getKey());
                    objWatchlist.ProductIDs.add(postsnapshot.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //If btnWatch is clicked
        btnWatch = findViewById(R.id.btnWatch);
        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Moving to watchlist class
                Intent intent = new Intent(getApplicationContext(), Watchlist.class);
                intent.putExtra("Extra_Watchlist", objWatchlist);
                startActivity(intent);
            }
        });

    }

    //Method to switch between fragments
    private void replaceFragment(Fragment fragment)
    {

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment2,fragment);
        fragmentTransaction.commit();
    }

}