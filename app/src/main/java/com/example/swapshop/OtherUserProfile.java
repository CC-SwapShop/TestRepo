package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        Bundle extras = getIntent().getExtras();
        objProduct = extras.getParcelable("Select_Product");
        //Get data from another activity
        Intent intent = getIntent();
        //objProduct = intent.getParcelableExtra("Select_Product");
        sPID = intent.getStringExtra("Select_ID");
        //sOGID = intent.getStringExtra("Extra_ongoingID");
        //objOnGoingSwap = intent.getParcelableExtra("Extra_ongoing");

        uname = findViewById(R.id.txtNameOther);
        uEmail= findViewById(R.id.txtEmailOther);
        user_profile = findViewById(R.id.profile_image);
        mRecyclerView = findViewById(R.id.recycle_view_OtherUser);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(objProduct.UID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uname.setText("" + snapshot.child("name").getValue().toString());
                uEmail.setText("" + snapshot.child("email").getValue().toString());
                Picasso.with(OtherUserProfile.this).load(snapshot.child("img").getValue().toString())
                        .fit().centerCrop().into(user_profile);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(OtherUserProfile.this, 2, RecyclerView.VERTICAL, false));
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

                    Product ojProduct = new Product(name,description,location,reqProduct,img,UID,status,category,swappedUID);


                    //Adding product to list
                    if(ojProduct.UID.equals(objProduct.UID) && ojProduct.checkSwapped() == false ){
                        productIDs.add(postsnapshot.getKey());
                        mUploads.add(ojProduct);
                    }
                }

                mAdapter = new UserInfoAdapter(OtherUserProfile.this,mUploads);
                mAdapter.setOnItemClickListener(new UserInfoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Product currProduct = mUploads.get(position);
                        String pID = productIDs.get(position);
                        Intent intent = new Intent(OtherUserProfile.this, ViewProduct.class);
                        intent.putExtra("Curr_Product", currProduct);
                        intent.putExtra("Extra_ID",pID);
                        //intent.putExtra("Extra_login",false);
                        startActivity(intent);
                    }
                });

                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }
}