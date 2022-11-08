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
    ImageButton btnAll;
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
        btnSearchProduct1 = view.findViewById(R.id.btnSEsearch2);
        btnAll = view.findViewById(R.id.ded1);
        mRecyclerView3 = view.findViewById(R.id.recycler_view3);
        btnHome3 = view.findViewById(R.id.button6);
        btnToys3 = view.findViewById(R.id.button7);
        btnGames3 = view.findViewById(R.id.button8);
        btnSport3 = view.findViewById(R.id.button9);
        btnOther3 =view.findViewById(R.id.button10);
        //Calling method
        listAll();

        //Home and appliances
        btnHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
               Bundle bundle = new Bundle();
                bundle.putString("sCategory","Home & Appliance");

                //New fragment with arguments
                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });

        //Other
        btnOther3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Other");

                //New fragment with arguments
                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });

        //Toys
        btnToys3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Toys");

                //New fragment with arguments
                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });

        //Games
        btnGames3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Games");

                //New fragment with arguments
                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });

        //Sport
        btnSport3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Sport");

                //New fragment with arguments
                SearchEveryone fragment = new SearchEveryone();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            }
        });
        //Using the search function
        btnSearchProduct1.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findProduct();
            }
        });

        //Using onClick for ListAll if user is in search
        btnAll.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAll();
            }
        });
        //Inflating view
        return view;
    }

    //List all
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

                if(productIDs.isEmpty()){
                    Toast.makeText(getContext(),"empty",Toast.LENGTH_SHORT).show();
                }else{
                    //Getting image
                    mAdapter = new ImageAdapter(getActivity(),mUploads);

                    //Getting product
                    mAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            //Toast.makeText(getContext(),"Swap click at: " + position,Toast.LENGTH_SHORT).show();
                            Product currProduct = mUploads.get(position);
                            String pID = productIDs.get(position);
                            Intent intent = new Intent(getContext(), ViewProduct.class);
                            intent.putExtra("Curr_Product", currProduct);
                            intent.putExtra("Extra_ID",pID);
                            intent.putExtra("Extra_login",false);
                            startActivity(intent);

                        }

                    });


                    mRecyclerView3.setAdapter(mAdapter);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
            }
        });





    }


    public void findProduct(){

        mRecyclerView3.setHasFixedSize(true);
        mRecyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));

        mUploads = new ArrayList<>();
        productIDs = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Products");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pName = edtSProductName1.getText().toString().trim();
                int iFound = 0;
                for(DataSnapshot postsnapshot: snapshot.getChildren()){

                    String name = postsnapshot.child("name").getValue().toString();
                    String description = postsnapshot.child("description").getValue().toString();
                    String location = postsnapshot.child("location").getValue().toString();
                    String img = postsnapshot.child("img").getValue().toString();
                    String UID = postsnapshot.child("UID").getValue().toString();
                    Boolean bSwap = (Boolean) postsnapshot.child("swapped").getValue();
                    String reqProduct = postsnapshot.child("reqProduct").getValue().toString();
                    String status = postsnapshot.child("status").getValue().toString();
                    String category = postsnapshot.child("category").getValue().toString();
                    String swappedUID = postsnapshot.child("swappedUID").getValue().toString();

                    Product objProduct = new Product(name,description,location,reqProduct,img,UID,status,category,swappedUID);
                    if(name.contains(pName)){
                        //if found
                        //Displaying the product

                        if(objProduct.checkSwapped() == false) {
                            iFound = 1;
                            mUploads.add(objProduct);
                            productIDs.add(postsnapshot.getKey());
                        }
                    }


                }
                if(iFound == 0){
                    Toast.makeText(getContext(),"No Results have been found" ,Toast.LENGTH_SHORT).show();
                    return;
                }

                mAdapter = new ImageAdapter(getActivity(),mUploads);
                mAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //Toast.makeText(getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid().toString(),Toast.LENGTH_SHORT).show();
                        if(FirebaseAuth.getInstance().getCurrentUser().getUid()==null){
                            Toast.makeText(getContext(), "login first",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Product currProduct = mUploads.get(position);
                        String pID = productIDs.get(position);
                        Intent intent = new Intent(getContext(), ViewProduct.class);
                        intent.putExtra("Curr_Product", currProduct);
                        intent.putExtra("Extra_ID",pID);
                        intent.putExtra("Extra_login",false);
                        startActivity(intent);

                    }

                    /*@Override
                    public void onWishlistClick(int position) {
                        Toast.makeText(getContext(),"Swap click at: " + position,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSwapped(int position) {
                        Toast.makeText(getContext(),"Swap click at: " + position,Toast.LENGTH_SHORT).show();
                    }*/
                });

                mRecyclerView3.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}