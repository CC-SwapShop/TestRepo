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
    TextView txtVP_Desc,txtVP_Name,txtVP_Loc,txtVP_UID,txtVP_ItemSwap, textStatus;
    ImageView imgVP_Prod;
    Button btnVP_swap, btnVP_AddWish, btnOtherUser;

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
        textStatus = findViewById(R.id.textStatus);
        imgVP_Prod = findViewById(R.id.imgVP_Prod);
        btnVP_swap = findViewById(R.id.btnVP_Swap);
        btnVP_AddWish = findViewById(R.id.btnVP_AddWishlist);
        btnOtherUser=findViewById(R.id.btnVP_Swap2);

        //initialise the texts on the interface
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(objProduct.UID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtVP_UID.setText("" + snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference initialise_reference = FirebaseDatabase.getInstance().getReference("Products")
                .child(sPID);

        initialise_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Load image from url
                Picasso.with(ViewProduct.this).load(snapshot.child("img").getValue().toString())
                        .fit().centerCrop().into(imgVP_Prod);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //txtVP_UID.setText("User ID:\n" + objProduct.UID);
        txtVP_Name.setText("" + objProduct.name);
        txtVP_Desc.setText("About this product:\n" + objProduct.description);
        txtVP_Loc.setText("Location:\n" + objProduct.location);
        txtVP_ItemSwap.setText("" + objProduct.reqProduct);
        textStatus.setText("Status:\n" + objProduct.status);



        //To do if bLogin bool is false
        if(bLogin == false || objProduct.checkSwapped()==true){
            btnVP_AddWish.setVisibility(View.INVISIBLE);
            btnVP_swap.setVisibility(View.INVISIBLE);
        }

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(user.equals(objProduct.UID)){
            btnVP_swap.setEnabled(false);
        }

        //Do if swap product is clicked
        btnVP_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwappProduct();
            }
        });

        btnOtherUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherProfile();
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
    public void otherProfile()
    {
        OnGoingSwaps onGoingSwaps = new OnGoingSwaps(FirebaseAuth.getInstance().getCurrentUser().getUid()
                ,objProduct.UID,sPID,true);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("OngoingSwaps");
        String key = ref.push().getKey();
        ref.child(key).setValue(onGoingSwaps);

        Intent intent = new Intent(getApplicationContext(), OtherUserProfile.class);
        intent.putExtra("Select_Product",objProduct);
        intent.putExtra("Select_ID",sPID);
        //intent.putExtra("Extra_ongoingID",key);
        //intent.putExtra("Extra_ongoing",onGoingSwaps);
        intent.putExtra("Extra_NOtLogin",bLogin);
        startActivity(intent);
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

        //Add to OngoingSwaps table
        OnGoingSwaps onGoingSwaps = new OnGoingSwaps(FirebaseAuth.getInstance().getCurrentUser().getUid()
                ,objProduct.UID,sPID,true);
        String onGoingSwapsKey = FirebaseAuth.getInstance().getCurrentUser().getUid() + sPID + objProduct.UID;
        FirebaseDatabase.getInstance().getReference().child("ProductOngoingSwaps").child(onGoingSwapsKey).setValue(onGoingSwaps);

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


        String key = "-NGMkH9tT85OpIjQUsmW";
        //Go to chat class for user to chat with user
        Intent intent = new Intent(getApplicationContext(), Chat2.class);
        //intent.putExtra("Select_Product",objProduct);
        intent.putExtra("Select_ID",sPID);
        intent.putExtra("Extra_ongoingID",onGoingSwapsKey);
        intent.putExtra("Extra_ongoing",onGoingSwaps);
        startActivity(intent);

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