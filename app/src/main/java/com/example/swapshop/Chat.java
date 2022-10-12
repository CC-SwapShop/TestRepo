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

    public Product objProduct;
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
        //objProduct = extras.getParcelable("Select_Product");
        //Get data from another activity
        Intent intent = getIntent();
        //objProduct = intent.getParcelableExtra("Select_Product");
        sPID = intent.getStringExtra("Select_ID");
        sOGID = intent.getStringExtra("Extra_ongoingID");
        objOnGoingSwap = intent.getParcelableExtra("Extra_ongoing");

        //Find ID with interface
        txtMProdDesc = findViewById(R.id.txtMProdDesc);
        txtMProdName = findViewById(R.id.txtMProdName);
        edtMessage = findViewById(R.id.edtMessage);
        fbtnMSend = findViewById(R.id.fbtnMSend);
        btnMAccept = findViewById(R.id.btnMAccept);
        btnMDecline = findViewById(R.id.btnMDecline);
        cardView = findViewById(R.id.cvButtons);

        //initialise the texts on the interface
        txtMProdName.setText("Car");
        txtMProdDesc.setText("Drives");

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


    }

}