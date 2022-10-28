package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchEveryone extends Fragment {

    //Variables for Search everyone class
    EditText edtSProductName1;
    ImageButton btnSearchProduct1;
    Button btnAll;
    Button btnHome3,btnToys3,btnGames3,btnSport3,btnOther3;

    //Private variables
    private RecyclerView mRecyclerView3;
    private ImageAdapter mAdapter;
    private DatabaseReference reference;
    private List<Product> mUploads;
    private List<String> productIDs;
    private String sCategory2;

    //Empty constructor needed for fragment
    public SearchEveryone() {
        // Required empty public constructor

    }

    //onCreateView method for fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_everyone, container, false);

        //pass data between fragments
        Bundle bundle = this.getArguments();
        sCategory2 = bundle.getString("sCategory");


        //Finding the corresponding Views
        edtSProductName1 = view.findViewById(R.id.edtSProductName1);
        //llSearch = view.findViewById(R.id.llSearchProduct);
        btnSearchProduct1 = view.findViewById(R.id.btnSEsearch);
        btnAll = view.findViewById(R.id.ded1);
        mRecyclerView3 = view.findViewById(R.id.recycler_view3);
        btnHome3 = view.findViewById(R.id.button6);
        btnToys3 = view.findViewById(R.id.button7);
        btnGames3 = view.findViewById(R.id.button8);
        btnSport3 = view.findViewById(R.id.button9);
        btnOther3 =view.findViewById(R.id.button10);
        //Calling method
        listAll();
        btnHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view

            }
        });
        btnOther3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view

            }
        });

        btnToys3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnGames3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSport3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),OtherUserProfile.class));
            }
        });
        //Using the search function
        btnSearchProduct1.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Using onClick for ListAll if user is in search
        btnAll.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //Inflating view
        return view;
    }

    public void listAll()
    {
        //getting data from database
        mRecyclerView3.setHasFixedSize(false);
        mRecyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));

        //Array lists
        mUploads = new ArrayList<>();
        productIDs = new ArrayList<>();

        //reference to database
        reference = FirebaseDatabase.getInstance().getReference().child("Products");

        //Finding items in database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    String name = postsnapshot.child("name").getValue().toString();
                    String description = postsnapshot.child("description").getValue().toString();
                    String location = postsnapshot.child("location").getValue().toString();
                    String img = postsnapshot.child("img").getValue().toString();
                    String UID = postsnapshot.child("UID").getValue().toString();
                    String reqProduct = postsnapshot.child("reqProduct").getValue().toString();
                    String status = postsnapshot.child("status").getValue().toString();
                    String category = postsnapshot.child("category").getValue().toString();
                    String swappedUID = postsnapshot.child("swappedUID").getValue().toString();

                    Product objProduct = new Product(name,description,location,reqProduct,img,UID,status,category,swappedUID);

                    //Adding product to list if item hasn't been swapped
                    if(objProduct.checkSwapped() == false && category.equals(sCategory2)){
                        productIDs.add(postsnapshot.getKey());
                        mUploads.add(objProduct);
                    }

                }

                //Getting image
                mAdapter = new ImageAdapter(getActivity(),mUploads);

                //Getting product
                mAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }

                    /*//Wishlist
                    @Override
                    public void onWishlistClick(int position) {
                        Toast.makeText(getContext(),"Swap click at: " + position,Toast.LENGTH_SHORT).show();
                    }

                    //Swapped
                    @Override
                    public void onSwapped(int position) {
                        Toast.makeText(getContext(),"Swap click at: " + position,Toast.LENGTH_SHORT).show();
                    }*/
                });

                mRecyclerView3.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

}