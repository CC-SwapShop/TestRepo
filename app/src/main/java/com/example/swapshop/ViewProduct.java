package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewProduct extends AppCompatActivity {

    //Variables for View product
    public Product objProduct;
    public String sPID;
    public boolean bLogin;
    TextView txtVP_Desc,txtVP_Name,txtVP_Loc,txtVP_UID,txtVP_ItemSwap;
    ImageView imgVP_Prod;
    Button btnVP_swap, btnVP_AddWish;

    //onCreate method for view product
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //using activity view product
        setContentView(R.layout.activity_view_product);

        //Pass Variables across
        Intent intent = getIntent();
        objProduct = intent.getParcelableExtra("Curr_Product");
        sPID = intent.getStringExtra("Extra_ID");
        bLogin = intent.getBooleanExtra("Extra_login",true);

        //Find ID with interface
        txtVP_Desc = findViewById(R.id.txtVP_Desc);
        txtVP_Name = findViewById(R.id.txtVP_Name);
        txtVP_Loc = findViewById(R.id.txtVP_Loc);
        txtVP_UID = findViewById(R.id.txtVP_UID);
        txtVP_ItemSwap = findViewById(R.id.txtVP_ItemSwap);
        imgVP_Prod = findViewById(R.id.imgVP_Prod);
        btnVP_swap = findViewById(R.id.btnVP_Swap);
        btnVP_AddWish = findViewById(R.id.btnVP_AddWishlist);

        //initialise the texts on the interface
        txtVP_UID.setText("User ID:\n" + objProduct.UID);
        txtVP_Name.setText("name:\n" + objProduct.name);
        txtVP_Desc.setText("Description:\n" + objProduct.description);
        txtVP_Loc.setText("Location:\n" + objProduct.location);
        txtVP_ItemSwap.setText("User would like:\n" + objProduct.reqProduct);

        //Load image from url
        Picasso.with(this).load(objProduct.img).fit().centerCrop().into(imgVP_Prod);

        //To do if bLogin bool is false
        if(bLogin == false || objProduct.checkSwapped()==true){
            btnVP_AddWish.setVisibility(View.INVISIBLE);
            btnVP_swap.setVisibility(View.INVISIBLE);
        }

        //Do if swap product is clicked
        btnVP_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwappProduct();
            }
        });

        //Do if add to wish list is clicked
        btnVP_AddWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddtoWishList();
            }
        });
    }

    //Notification to users phone
    public void MakeandSendNotification(){
        Notification notification;
        NotificationManagerCompat notificationManagerCompat;


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("myCh","My Channel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //What notification should include
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"myCh")
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentTitle("Watchlist")
                .setContentText("Product " + objProduct.name + " has been swapped");

        //Build
        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        //Send notification
        notificationManagerCompat.notify(999,notification);
    }

    //Function to mark product as swapped and send notification
    public void SwappProduct(){
        objProduct.setStatusOfferMade();
        FirebaseDatabase.getInstance().getReference("Products").child(sPID).child("status").setValue("Offer Made");

        //Database reference
        DatabaseReference wReference = FirebaseDatabase.getInstance().getReference().child("Watchlist")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Send notification
        wReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    String sKey = postsnapshot.getKey();
                    if(sKey.equals(sPID)){
                        MakeandSendNotification();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Go to chat class for user to chat with user //To be implemented in future sprints
        startActivity(new Intent(getApplicationContext(), Chat.class));

        //Message to say product has been requested as confirmation
        Toast.makeText(ViewProduct.this,"Product has been requested for swap",Toast.LENGTH_SHORT).show();
    }

    //Adding item to watch list
    public void AddtoWishList(){

        //Firebase reference
        FirebaseDatabase.getInstance().getReference("Watchlist")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(sPID).child("SwappedChecked").setValue(false)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        //If successful
                        if (task.isSuccessful()){
                            Toast.makeText(ViewProduct.this,"Product added to watchlist",Toast.LENGTH_SHORT).show();

                        //If not successful
                        }else{
                            Toast.makeText(ViewProduct.this,"Unable to add to watchlist",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}