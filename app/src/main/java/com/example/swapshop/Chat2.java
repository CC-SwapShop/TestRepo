package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Chat2 extends AppCompatActivity {

    //Variables
    private String sPID,sOGID;
    private Product objProduct;
    private OnGoingSwaps objOnGoingSwap;
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    Button done, cancel;
    ImageButton star1, star2, star3, star4, star5;
    int rating = 5;

    //Views
    TextView txtMProdName, txtMProdDesc;
    ImageView imgMProduct;
    EditText edtMessage;
    FloatingActionButton fbtnMSend;
    Button btnMDecline, btnMAccept;
    List<String> productOngoing;
    List<String> arrMesssages;
    DatabaseReference reference;
    DatabaseReference pReference;
    DatabaseReference referenceChat;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        //From previous class
        Intent intent = getIntent();
        //objProduct = intent.getParcelableExtra("Select_Product");
        sPID = intent.getStringExtra("Select_ID");
        sOGID = intent.getStringExtra("Extra_ongoingID");
        objOnGoingSwap = intent.getParcelableExtra("Extra_ongoing");

        //Find ID with interface
        txtMProdDesc = findViewById(R.id.txtMProdDesc1);
        txtMProdName = findViewById(R.id.txtMProdName1);
        imgMProduct = findViewById(R.id.imgMProduct1);
        edtMessage = findViewById(R.id.edtMessage1);
        fbtnMSend = findViewById(R.id.fbtnMSend1);
        btnMAccept = findViewById(R.id.btnMAccept1);
        btnMDecline = findViewById(R.id.btnMDecline1);
        cardView = findViewById(R.id.cvButtons1);

        //Getting user
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();


        if(user.equals(objOnGoingSwap.customer)){
            btnMAccept.setVisibility(View.INVISIBLE);
            btnMDecline.setVisibility(View.INVISIBLE);
        }

        //message view
        mRecyclerView = findViewById(R.id.recyclerView_message1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager((this)));

        //initialise the texts on the interface
        pReference = FirebaseDatabase.getInstance().getReference().child("Products");
        pReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    if(postsnapshot.getKey().equals(sPID)){
                        String name = postsnapshot.child("name").getValue().toString();
                        String description = postsnapshot.child("description").getValue().toString();
                        String img = postsnapshot.child("img").getValue().toString();
                        txtMProdName.setText(name);
                        txtMProdDesc.setText(description);
                        Picasso.with(getApplicationContext()).load(img).fit().centerCrop().into(imgMProduct);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //populate array for ongoing
        productOngoing = new ArrayList<>();

        //Database reference
        reference = FirebaseDatabase.getInstance().getReference().child("OngoingSwaps");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productOngoing.clear();
                for(DataSnapshot  postsnapshot: snapshot.getChildren()){
                    String oID = postsnapshot.child("productId").getValue().toString();
                    if(oID.equals(sPID)){
                        productOngoing.add(postsnapshot.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Array list
        arrMesssages =  new ArrayList<>();
        referenceChat = FirebaseDatabase.getInstance().getReference().child("Chats").child(sOGID);
        referenceChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrMesssages.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    //String to = postsnapshot.child("to").getValue().toString();
                    String sMessage = postsnapshot.child("message").getValue().toString();
                    //String from = postsnapshot.child("from").getValue().toString();
                    //ChatMessage chatMessage = new ChatMessage(from,sMessage,to);
                    //arrChatMessages.add(chatMessage);
                    //Toast.makeText(Chat2.this,sMessage,Toast.LENGTH_SHORT).show();
                    arrMesssages.add(sMessage);

                }

                //Adapter
                mAdapter = new MessageAdapter(Chat2.this,arrMesssages);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //actions
        btnMDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeclineOffer();
            }
        });

        //accept
        btnMAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptOffer();
            }
        });

        //send
        fbtnMSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }
        });

    }

    //Functions
    public void DeclineOffer(){
        FirebaseDatabase.getInstance().getReference("OngoingSwaps").child(sOGID)
                .child("ongoing").setValue(false);

        //Message to say product has been declined
        Toast.makeText(Chat2.this,"Offer has been declined",Toast.LENGTH_SHORT).show();
    }

    public void AcceptOffer(){
        //Change status of product

        FirebaseDatabase.getInstance().getReference("Products").child(sPID).child("status")
                .setValue("swapped");

        FirebaseDatabase.getInstance().getReference("Products").child(sPID).child("swappedUID")
                .setValue(objOnGoingSwap.customer);

        //Set all ongoing offers for product to false
        for (String temp : productOngoing) {
            FirebaseDatabase.getInstance().getReference("OngoingSwaps").child(temp)
                    .child("ongoing").setValue(false);
        }
        //add item to Accepted swap
        AcceptedSwap acceptedSwap = new AcceptedSwap(objOnGoingSwap.customer
                , objOnGoingSwap.provider, objOnGoingSwap.productId);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("AcceptedSwaps");
        String key = ref.push().getKey();
        ref.child(key).setValue(acceptedSwap);

        //Message to say product has been declined
        Toast.makeText(Chat2.this,"Offer has been Accepted",Toast.LENGTH_SHORT).show();
        //rating pop up here
        createNewRatingDialog();
    }

    //rating class
    public void createNewRatingDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View ratingPopup = getLayoutInflater().inflate(R.layout.rating_popup, null);

        dialogBuilder.setView((ratingPopup));
        dialog = dialogBuilder.create();
        dialog.show();

        star1 = (ImageButton) ratingPopup.findViewById(R.id.btn_star1);
        star2 = (ImageButton) ratingPopup.findViewById(R.id.btn_star2);
        star3 = (ImageButton) ratingPopup.findViewById(R.id.btn_star3);
        star4 = (ImageButton) ratingPopup.findViewById(R.id.btn_star4);
        star5 = (ImageButton) ratingPopup.findViewById(R.id.btn_star5);

        done = (Button) ratingPopup.findViewById(R.id.btnDone);
        cancel = (Button) ratingPopup.findViewById(R.id.btnCancel);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                star3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                star4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                star5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                rating = 1;
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                star4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                star5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                rating = 2;
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                star5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                rating = 3;
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                rating = 4;
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                star5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                rating = 5;
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set new user rating based off this rating
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void SendMessage(){
        String sMessage = edtMessage.getText().toString().trim();
        //Database references
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String provider= objOnGoingSwap.provider;

        if(user.equals(provider)){
            provider = objOnGoingSwap.customer;
        }




        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats").child(sOGID);
        String key = ref.push().getKey();
        ref.child(key).child("message").setValue(sMessage);
        ref.child(key).child("from").setValue(user);
        ref.child(key).child("to").setValue(provider);

        /*Intent intent = new Intent( getApplicationContext(), Chat2.class);
        intent.putExtra("Select_ID",sPID);
        intent.putExtra("Extra_ongoingID",sOGID);
        intent.putExtra("Extra_ongoing",objOnGoingSwap);

        startActivity(intent);*/
    }
}