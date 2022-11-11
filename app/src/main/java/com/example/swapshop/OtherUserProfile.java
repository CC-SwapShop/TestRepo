package com.example.swapshop;

import static androidx.test.InstrumentationRegistry.getContext;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherUserProfile extends AppCompatActivity {

    //Variables defined
    public Product objProduct;
    public String sPID;
    private CircleImageView user_profile;
    public boolean bLogin;
    TextView uname, uEmail;
    private RecyclerView mRecyclerView;
    private UserInfoAdapter mAdapter;
    private DatabaseReference reference;
    private List<Product> mUploads;
    private List<String> productIDs;
    ImageButton star1, star2, star3, star4, star5;
    long rating=5;
    private Boolean isbLogin;


    //OnCreate method for OtherUserProfile
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        //Bundles create to get information from previous activity
        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        objProduct = intent.getParcelableExtra("Select_Product");
        sPID = intent.getStringExtra("Select_ID");
        bLogin = intent.getBooleanExtra("Extra_NOtLogin",true);


        //Getting text views
        uname = findViewById(R.id.txtNameOther);
        uEmail= findViewById(R.id.txtEmailOther);
        user_profile = findViewById(R.id.profile_image);
        mRecyclerView = findViewById(R.id.recycle_view_OtherUser);

        //Buttons for star view
        star1 = findViewById(R.id.btn_star_OU1);
        star2 = findViewById(R.id.btn_star_OU2);
        star3 = findViewById(R.id.btn_star_OU3);
        star4 = findViewById(R.id.btn_star_OU4);
        star5 = findViewById(R.id.btn_star_OU5);

        //Database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(objProduct.UID);
        reference.addValueEventListener(new ValueEventListener() {

            //Getting information from databsase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uname.setText("" + snapshot.child("name").getValue().toString());
                uEmail.setText("" + snapshot.child("email").getValue().toString());
                Picasso.with(OtherUserProfile.this).load(snapshot.child("img").getValue().toString())
                        .fit().centerCrop().into(user_profile);
                rating = (long) snapshot.child("rating").getValue(Long.class);
                FillStars(rating);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Recycle views
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(OtherUserProfile.this, 2, RecyclerView.VERTICAL, false));
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

                    Product ojProduct = new Product(name,description,location,reqProduct,img,UID,status,category,swappedUID);


                    //Adding product to list
                    if(ojProduct.UID.equals(objProduct.UID) && ojProduct.checkSwapped() == false ){
                        productIDs.add(postsnapshot.getKey());
                        mUploads.add(ojProduct);
                    }
                }

                //Adapters
                mAdapter = new UserInfoAdapter(OtherUserProfile.this,mUploads);
                mAdapter.setOnItemClickListener(new UserInfoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Product currProduct = mUploads.get(position);
                        String pID = productIDs.get(position);
                        Intent intent = new Intent(OtherUserProfile.this, ViewProduct.class);
                        intent.putExtra("Curr_Product", currProduct);
                        intent.putExtra("Extra_ID",pID);
                        intent.putExtra("Extra_login",bLogin);
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


        }
        //Method to fill stars
    public void FillStars(long rated){
        int rating = (int) Math.round(rated);
        star1.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_on));
        star2.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_on));
        star3.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_on));
        star4.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_on));
        star5.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_on));

        //Depending on rating
        if(rating < 5){
            star5.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_off));
            if(rating < 4){
                star4.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_off));
                if(rating < 3){
                    star3.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_off));
                    if(rating < 2){
                        star2.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_off));
                        if(rating < 1){
                            star1.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.btn_star_big_off));
                        }
                    }
                }
            }
        }
    }
}