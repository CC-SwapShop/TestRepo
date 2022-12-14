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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment {

    //Variables for Search class
    EditText edtSProductName;
    LinearLayout llSearch;
    ImageButton btnSearchProduct;
    ImageButton btnAll;
    Button btnHome1,btnToys1,btnGames1,btnSport1, btnOther1;

    //Private variables
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private DatabaseReference reference;
    private List<Product> mUploads;
    private List<String> productIDs;
    private String sCategory;
    private TextView txtNoItems;


    //Empty constructor needed for fragment
    public Search(){
        // require a empty public constructor
    }

    //onCreateView method for fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);


        //pass data between fragments
        Bundle bundle = this.getArguments();
        sCategory = bundle.getString("sCategory");


        //Finding the corresponding Views
        edtSProductName = view.findViewById(R.id.edtSProductName);
        //llSearch = view.findViewById(R.id.llSearchProduct);
        btnSearchProduct = view.findViewById(R.id.button);
        btnAll = view.findViewById(R.id.ded);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        btnHome1 = view.findViewById(R.id.button2);
        btnToys1 = view.findViewById(R.id.button3);
        btnGames1 = view.findViewById(R.id.button4);
        btnSport1 = view.findViewById(R.id.button5);
        btnOther1= view.findViewById(R.id.button11);
        txtNoItems = view.findViewById(R.id.txtSNoItemsS);

        //Calling method
        listAll();
        btnOther1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Other");

                //New fragment with arguments
                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });
        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Home & Appliance");

                //New fragment with arguments
                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });

        btnToys1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Toys");

                //New fragment with arguments
                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });

        btnGames1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Games");
                //New fragment with arguments
                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });

        btnSport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change view
                Bundle bundle = new Bundle();
                bundle.putString("sCategory","Sport");

                //New fragment with arguments
                Search fragment = new Search();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flFragment2,fragment).commit();
            }
        });

        //Using the search function
        btnSearchProduct.setOnClickListener(new android.view.View.OnClickListener() {
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


    //Listing all
    public void listAll()
    {
        txtNoItems.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        //getting data from database
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


                    //Adding product to list
                    if(objProduct.checkSwapped() == false && category.equals(sCategory) ){
                        productIDs.add(postsnapshot.getKey());
                        mUploads.add(objProduct);
                    }
                }
                if(productIDs.isEmpty()){
                    //Toast.makeText(getContext(),"empty",Toast.LENGTH_SHORT).show();
                    txtNoItems.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }else {
                    //Getting image
                    mAdapter = new ImageAdapter(getActivity(), mUploads);

                    //Getting product
                    mAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            //Toast.makeText(getContext(),"Swap click at: " + position,Toast.LENGTH_SHORT).show();
                            Product currProduct = mUploads.get(position);
                            String pID = productIDs.get(position);
                            Intent intent = new Intent(getContext(), ViewProduct.class);
                            intent.putExtra("Curr_Product", currProduct);
                            intent.putExtra("Extra_ID", pID);
                            startActivity(intent);

                        }

                    });

                    mRecyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void findProduct(){

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
                        Toast.makeText(getContext(), currProduct.name + " " + pID,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), ViewProduct.class);
                        intent.putExtra("Curr_Product", currProduct);
                        intent.putExtra("Extra_ID",pID);
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