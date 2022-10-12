package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Chat2 extends AppCompatActivity {

    private String sPID,sOGID;
    private Product objProduct;
    private OnGoingSwaps objOnGoingSwap;
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);



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


        arrMesssages =  new ArrayList<>();

        referenceChat = FirebaseDatabase.getInstance().getReference().child("Chats").child(sOGID);

        referenceChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    String to = postsnapshot.child("to").getValue().toString();
                    String sMessage = postsnapshot.child("message").getValue().toString();
                    String from = postsnapshot.child("from").getValue().toString();
                    //ChatMessage chatMessage = new ChatMessage(from,sMessage,to);
                    //arrChatMessages.add(chatMessage);
                    //Toast.makeText(Chat2.this,sMessage,Toast.LENGTH_SHORT).show();
                    arrMesssages.add(sMessage);

                }

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
    }

    public void SendMessage(){
        String sMessage = edtMessage.getText().toString().trim();

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

        Intent intent = new Intent( getApplicationContext(), Chat2.class);
        intent.putExtra("Select_ID",sPID);
        intent.putExtra("Extra_ongoingID",sOGID);
        intent.putExtra("Extra_ongoing",objOnGoingSwap);

        startActivity(intent);
    }
}