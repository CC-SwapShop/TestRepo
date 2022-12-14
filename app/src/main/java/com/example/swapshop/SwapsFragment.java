package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SwapsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private DatabaseReference reference;
    private List<Product> mUploads;
    private List<String> productIDs;

    //empty constructor required for fragment
    public SwapsFragment() {
        // Required empty public constructor
    }

    //onCreateView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_swaps, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_CompletedSwaps);


        listAllSwaps();
        return  view;
    }

    public void listAllSwaps(){
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

                    //From database
                    String name = postsnapshot.child("name").getValue().toString();
                    String description = postsnapshot.child("description").getValue().toString();
                    String location = postsnapshot.child("location").getValue().toString();
                    String img = postsnapshot.child("img").getValue().toString();
                    String UID = postsnapshot.child("UID").getValue().toString();
                    String reqProduct = postsnapshot.child("reqProduct").getValue().toString();
                    String status = postsnapshot.child("status").getValue().toString();
                    String category = postsnapshot.child("category").getValue().toString();
                    String swappedUID = postsnapshot.child("swappedUID").getValue().toString();
                    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Product objProduct = new Product(name,description,location,reqProduct,img,UID,status,category,swappedUID);

                    //Adding product to list
                    if(objProduct.checkSwapped() == true){
                        if(user.equals(UID)||user.equals(swappedUID)){{
                            productIDs.add(postsnapshot.getKey());
                            mUploads.add(objProduct);
                        }}

                    }
                }

                //Getting image
                mAdapter = new ImageAdapter(getActivity(),mUploads);

                //Getting product
                mAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Product currProduct = mUploads.get(position);
                        String pID = productIDs.get(position);
                        Intent intent = new Intent(getContext(), ViewProduct.class);
                        intent.putExtra("Curr_Product", currProduct);
                        intent.putExtra("Extra_ID",pID);
                        intent.putExtra("Extra_login",false);
                        startActivity(intent);

                    }
                });

                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
            }
        });
    }

}