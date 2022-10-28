package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Chat extends AppCompatActivity {

    private Product objProduct;
    private String sPID,sOGID;
    private OnGoingSwaps objOnGoingSwap;
    TextView txtMProdName, txtMProdDesc;
    ImageView imgMProduct;
    EditText edtMessage;
    FloatingActionButton fbtnMSend;
    Button btnMDecline, btnMAccept;
    List<String> productOngoing;
    DatabaseReference reference;
    DatabaseReference referenceChat;
    CardView cardView;

    @Override
    //Java class for chat function to be implemented in future
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        objProduct = extras.getParcelable("Select_Product");
        //Get data from another activity
        Intent intent = getIntent();
        //objProduct = intent.getParcelableExtra("Select_Product");
        sPID = intent.getStringExtra("Select_ID");
        sOGID = intent.getStringExtra("Extra_ongoingID");
        objOnGoingSwap = intent.getParcelableExtra("Extra_ongoing");

        //Find ID with interface
        txtMProdDesc = findViewById(R.id.txtMProdDesc);
        txtMProdName = findViewById(R.id.txtMProdName);
        imgMProduct = findViewById(R.id.imgMProduct);
        edtMessage = findViewById(R.id.edtMessage);
        fbtnMSend = findViewById(R.id.fbtnMSend);
        btnMAccept = findViewById(R.id.btnMAccept);
        btnMDecline = findViewById(R.id.btnMDecline);
        cardView = findViewById(R.id.cvButtons);

        //initialise the texts on the interface
        txtMProdName.setText(objProduct.name);
        txtMProdDesc.setText(objProduct.description);
        Picasso.with(this).load(objProduct.img).fit().centerCrop().into(imgMProduct);

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(user.equals(objOnGoingSwap.provider)==false){
            cardView.setVisibility(View.INVISIBLE);
        }

        //populate array for ongoing
        productOngoing = new ArrayList<>();

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


        //actions
        btnMDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeclineOffer();
            }
        });

        btnMAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptOffer();
            }
        });

        fbtnMSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }
        });

    }

    //Decline offer
    public void DeclineOffer(){
        FirebaseDatabase.getInstance().getReference("OngoingSwaps").child(sOGID)
                .child("ongoing").setValue(false);

        //Message to say product has been declined
        Toast.makeText(Chat.this,"Offer has been declined",Toast.LENGTH_SHORT).show();
    }

    //Accept offer
    public void AcceptOffer(){
        //Change status of product
        objProduct.setStatusSwapped();
        FirebaseDatabase.getInstance().getReference("Products").child(sPID).child("status")
                .setValue("swapped");

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
        Toast.makeText(Chat.this,"Offer has been Accepted",Toast.LENGTH_SHORT).show();
    }

    //Sending message
    public void SendMessage(){
        String sMessage = edtMessage.getText().toString().trim();

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String provider= objProduct.UID;

        if(user.equals(provider)){
            provider = objOnGoingSwap.customer;
        }
        //ChatMessage chatMessage = new ChatMessage(user,sMessage,provider);
        //Database references
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats").child(sOGID);
        String key = ref.push().getKey();
        ref.child(key).child("message").setValue(sMessage);
        ref.child(key).child("from").setValue(user);
        ref.child(key).child("to").setValue(provider);


        //Starting chat 2
        Intent intent = new Intent( getApplicationContext(), Chat2.class);
        intent.putExtra("Select_ID",sPID);
        intent.putExtra("Extra_ongoingID",sOGID);
        intent.putExtra("Extra_ongoing",objOnGoingSwap);

        startActivity(intent);
    }
}