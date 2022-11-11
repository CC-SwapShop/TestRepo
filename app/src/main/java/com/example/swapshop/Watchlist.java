package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Watchlist extends AppCompatActivity implements WatchlistAdapter.OnItemClickListener{

    //Variables for watch list class
    private RecyclerView mRecyclerView;
    private WatchlistAdapter mAdapter;

    //Firebase references
    private DatabaseReference reference;
    private DatabaseReference wReference;

    //Lists
    private List<Product> mUploads;
    private List<String> productIDs;
    private UserWatchlist objWatchlist;

    //OnCreate for watchlist
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Using watchlist class
        setContentView(R.layout.activity_watchlist);

        //Adapter
        objWatchlist = getIntent().getParcelableExtra("Extra_Watchlist");


        //Recyclers
        mRecyclerView = findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Array lists
        mUploads = new ArrayList<>();
        productIDs = new ArrayList<>();

        //reference
        reference = FirebaseDatabase.getInstance().getReference().child("Products");

        //Getting product
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    if(objWatchlist.ProductIDs.contains(postsnapshot.getKey())){
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

                        //new Product
                        Product objProduct = new Product(name,description,location,reqProduct,img,UID,status,category,swappedUID);

                        //Adding to list
                        mUploads.add(objProduct);
                    }

                }

                //Adapter
                mAdapter = new WatchlistAdapter(Watchlist.this, mUploads);
                mAdapter.setOnItemClickListener(Watchlist.this);

                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Watchlist.this,"error",Toast.LENGTH_SHORT).show();
            }
        });

    }

    //If the item is clicked the user should be directed to view product
    @Override
    public void onItemClick(int position) {
        Product currProduct = mUploads.get(position);
        String pID = objWatchlist.ProductIDs.get(position);

        if(currProduct.checkSwapped()==true){
            FirebaseDatabase.getInstance().getReference("Watchlist")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(pID).child("SwappedChecked").setValue(true);
        }
        Intent intent = new Intent(getApplicationContext(), ViewProduct.class);
        intent.putExtra("Curr_Product", currProduct);
        intent.putExtra("Extra_ID",pID);
        startActivity(intent);
    }

}