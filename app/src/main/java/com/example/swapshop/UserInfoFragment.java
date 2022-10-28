package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserInfoFragment extends Fragment {

    //Variables defined
    TextView txtUI_name,txtEmailOther;
    CircleImageView imgUIimage;
    Button btnSignOut, btnupdate;
    private RecyclerView mRecyclerView;
    private UserInfoAdapter mAdapter;
    private DatabaseReference reference;
    private List<Product> mUploads;
    private List<String> productIDs;
    ImageButton star1, star2, star3, star4, star5;
    long rating=5;

    // Required empty public constructor
    public UserInfoFragment() {
        // Required empty public constructor
    }

    //OnCreate method for UserInfoFragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        //find view components
        txtUI_name = view.findViewById(R.id.txtUI_name);
        btnSignOut = view.findViewById(R.id.btnSignout);
        btnupdate = view.findViewById(R.id.btnupdate);
        mRecyclerView = view.findViewById(R.id.recyclerView_UserInfo);
        imgUIimage = view.findViewById(R.id.imgYIimage);
        txtEmailOther=view.findViewById(R.id.txtEmailOther2);

        //Buttons for star view
        star1 = view.findViewById(R.id.btn_star_CU1);
        star2 = view.findViewById(R.id.btn_star_CU2);
        star3 = view.findViewById(R.id.btn_star_CU3);
        star4 = view.findViewById(R.id.btn_star_CU4);
        star5 = view.findViewById(R.id.btn_star_CU5);

        //initialise values
        //Database reference
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference refs = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        refs.addValueEventListener(new ValueEventListener() {
            //Getting information from databsase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtUI_name.setText(snapshot.child("name").getValue().toString());
                txtEmailOther.setText(snapshot.child("email").getValue().toString());
                Picasso.with(getContext()).load(snapshot.child("img").getValue().toString())
                        .fit().centerCrop().into(imgUIimage);
                rating = (long) snapshot.child("rating").getValue(Long.class);
                FillStars(rating);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Recycle views
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        mUploads = new ArrayList<>();
        productIDs = new ArrayList<>();

        //Reference to database
        reference = FirebaseDatabase.getInstance().getReference().child("Products");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren()){

                    //Getting information from database
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

                //Adapters
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

                //Setting the products view
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

        //On click for go to update details
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changing activity
                startActivity(new Intent(getContext(), update_details_activity.class));
            }
        });

        return view;
    }

    //User sign out
    public void SignUserOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), Home.class));
    }

    //Filling stars
    public void FillStars(long rated){
        int rating = (int) Math.round(rated);
        star1.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));
        star2.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));
        star3.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));
        star4.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));
        star5.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));

        //Depending on rating
        if(rating < 5){
            star5.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_off));
            if(rating < 4){
                star4.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_off));
                if(rating < 3){
                    star3.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_off));
                    if(rating < 2){
                        star2.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_off));
                        if(rating < 1){
                            star1.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_off));
                        }
                    }
                }
            }
        }
    }
}