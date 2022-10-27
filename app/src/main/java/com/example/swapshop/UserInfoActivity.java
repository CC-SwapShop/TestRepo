package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        //find view components
        txtUI_name = findViewById(R.id.txtUI_name);
        btnSignOut = findViewById(R.id.btnUI_Signout);
        btnupdate = findViewById(R.id.btnupdate);
        mRecyclerView = findViewById(R.id.recyclerView_UserInfo);
        imgUIimage = findViewById(R.id.imgYIimage);
        txtEmailOther = findViewById(R.id.txtEmailOther2);

        star1 = findViewById(R.id.btn_star_CU1);
        star2 = findViewById(R.id.btn_star_CU2);
        star3 = findViewById(R.id.btn_star_CU3);
        star4 = findViewById(R.id.btn_star_CU4);
        star5 = findViewById(R.id.btn_star_CU5);

        //initialise values
        String user = "U13UWwzr6uMtsZj2lHTDYMnlyDn2";
        DatabaseReference refs = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtUI_name.setText(snapshot.child("name").getValue().toString());
                txtEmailOther.setText(snapshot.child("email").getValue().toString());
                Picasso.with(UserInfoActivity.this).load(snapshot.child("img").getValue().toString())
                        .fit().centerCrop().into(imgUIimage);
                rating = (long) snapshot.child("rating").getValue(Long.class);
                FillStars(rating);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(UserInfoActivity.this, 2, RecyclerView.VERTICAL, false));
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

                mAdapter = new UserInfoAdapter(UserInfoActivity.this,mUploads);
                /*mAdapter.setOnItemClickListener(new UserInfoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Product currProduct = mUploads.get(position);
                        String pID = productIDs.get(position);
                        Intent intent = new Intent(UserInfoActivity.this, ViewProduct.class);
                        intent.putExtra("Curr_Product", currProduct);
                        intent.putExtra("Extra_ID",pID);
                        intent.putExtra("Extra_login",false);
                        startActivity(intent);
                    }
                });*/

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

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(UserInfoActivity.this, update_details_activity.class));
            }
        });
    }

    public void SignUserOut(){
        //FirebaseAuth.getInstance().signOut();
        //startActivity(new Intent(UserInfoActivity.this, Home.class));
    }

    public void FillStars(long rated){
        int rating = (int) Math.round(rated);
        star1.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_on));
        star2.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_on));
        star3.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_on));
        star4.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_on));
        star5.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_on));

        if(rating < 5){
            star5.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_off));
            if(rating < 4){
                star4.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_off));
                if(rating < 3){
                    star3.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_off));
                    if(rating < 2){
                        star2.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_off));
                        if(rating < 1){
                            star1.setImageDrawable(ContextCompat.getDrawable(UserInfoActivity.this,android.R.drawable.btn_star_big_off));
                        }
                    }
                }
            }
        }
    }

}