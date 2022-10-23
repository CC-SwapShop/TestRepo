package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class UserInfoFragment extends Fragment {
    TextView txtUI_name;
    Button btnSignOut;
    private RecyclerView mRecyclerView;
    private UserInfoAdapter mAdapter;
    private DatabaseReference reference;
    private List<Product> mUploads;
    private List<String> productIDs;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        //find view components
        txtUI_name = view.findViewById(R.id.txtUI_name);
        btnSignOut = view.findViewById(R.id.btnSignout);
        mRecyclerView = view.findViewById(R.id.recyclerView_UserInfo);

        //initialise values
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference refs = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtUI_name.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        mUploads = new ArrayList<>();
        productIDs = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Products");

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
                    if(objProduct.UID.equals(user) ){
                        productIDs.add(postsnapshot.getKey());
                        mUploads.add(objProduct);
                    }
                }

                mAdapter = new UserInfoAdapter(getActivity(),mUploads);
                mAdapter.setOnItemClickListener(new UserInfoAdapter.OnItemClickListener() {
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

            }
        });

        //on click events
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUserOut();
            }
        });

        return view;
    }

    public void SignUserOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), Home.class));
    }
}