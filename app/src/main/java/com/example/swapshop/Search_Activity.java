package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search_Activity extends AppCompatActivity {
        //Variables for Search class
        EditText edtSProductName;
        LinearLayout llSearch;
        ImageButton btnSearchProduct;
        Button btnAll;
        Button btnHome1,btnToys1,btnGames1,btnSport1, btnOther1;

        //Private variables
        private RecyclerView mRecyclerView;
        private ImageAdapter mAdapter;
        private DatabaseReference reference;
        private List<Product> mUploads;
        private List<String> productIDs;
        private String sCategory;
        public String unitTest = "True";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_search);

            //Bundle bundle = this.getArguments();
            //sCategory = bundle.getString("sCategory");

            //Finding the corresponding Views
            edtSProductName = findViewById(R.id.edtSProductName);
            //llSearch = view.findViewById(R.id.llSearchProduct);
            btnSearchProduct = findViewById(R.id.button);
            btnAll = findViewById(R.id.ded);
            mRecyclerView = findViewById(R.id.recycler_view);
            btnHome1 = findViewById(R.id.button2);
            btnToys1 = findViewById(R.id.button3);
            btnGames1 = findViewById(R.id.button4);
            btnSport1 = findViewById(R.id.button5);
            btnOther1= findViewById(R.id.button11);

            btnOther1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //change view
                    Bundle bundle = new Bundle();
                    bundle.putString("sCategory","Other");

                    com.example.swapshop.Search fragment = new com.example.swapshop.Search();
                    fragment.setArguments(bundle);
                }
            });


            btnGames1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //change view
                    Bundle bundle = new Bundle();
                    bundle.putString("sCategory","Games");

                    com.example.swapshop.Search fragment = new com.example.swapshop.Search();
                    fragment.setArguments(bundle);
                }
            });

            btnSport1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //change view
                    Bundle bundle = new Bundle();
                    bundle.putString("sCategory","Sport");

                    com.example.swapshop.Search fragment = new com.example.swapshop.Search();
                    fragment.setArguments(bundle);
                }
            });

            //Using the search function
            btnSearchProduct.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findProduct();
                }
            });

        }


        public void findProduct(){

            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            mUploads = new ArrayList<>();
            productIDs = new ArrayList<>();

            reference = FirebaseDatabase.getInstance().getReference().child("Products");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String pName = edtSProductName.getText().toString().trim();
                    int iFound = 0;
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
                        if(name.contains(pName)){
                            //if found
                            //Displaying the product
                            if(objProduct.checkSwapped() == false){
                                iFound = 1;
                                mUploads.add(objProduct);
                                productIDs.add(postsnapshot.getKey());
                            }
                        }


                    }
                    if(iFound == 0){
                        Toast.makeText(Search_Activity.this,"No Results have been found" ,Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAdapter = new ImageAdapter(Search_Activity.this,mUploads);
                    mAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            //Toast.makeText(getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid().toString(),Toast.LENGTH_SHORT).show();
                            if(FirebaseAuth.getInstance().getCurrentUser().getUid()==null){
                                Toast.makeText(Search_Activity.this, "login first",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Product currProduct = mUploads.get(position);


                            String pID = productIDs.get(position);
                            Toast.makeText(Search_Activity.this, currProduct.name + " " + pID,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Search_Activity.this, ViewProduct.class);
                            intent.putExtra("Extra_ID",pID);
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

                    mRecyclerView.setAdapter(mAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Search_Activity.this,"error",Toast.LENGTH_SHORT).show();
                    unitTest = "False";
                }
            });
        }


}
