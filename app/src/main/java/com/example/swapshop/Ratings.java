package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Ratings extends AppCompatActivity {

    //Variables
    private OnGoingSwaps objOnGoingSwap;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    Button done, cancel;
    ImageButton star1, star2, star3, star4, star5;
    int rating = 5;
    int rcount=1, curr_totalrating=5;

    //Database reference
    DatabaseReference referenceRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        //CreateRatingsDialog
        //createNewRatingDialog();
    }

    //rating class
    public void createNewRatingDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View ratingPopup = getLayoutInflater().inflate(R.layout.rating_popup, null);
        ratingPopup.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

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

        star1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
        star2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
        star3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
        star4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
        star5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
        rating = 1;

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

                referenceRate = FirebaseDatabase.getInstance().getReference().child("Users");
                referenceRate.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot postsnapshot: snapshot.getChildren()){
                            if(postsnapshot.getKey().equals(objOnGoingSwap.customer)){
                                rcount = (int) postsnapshot.child("rcount").getValue(Integer.class);
                                curr_totalrating = (int) postsnapshot.child("totalratings").getValue(Integer.class);

                                FirebaseDatabase.getInstance().getReference().child(("Users"))
                                        .child(objOnGoingSwap.customer).child("rating").setValue((curr_totalrating+rating)/(rcount+1));

                                FirebaseDatabase.getInstance().getReference().child(("Users"))
                                        .child(objOnGoingSwap.customer).child("totalratings").setValue((curr_totalrating+rating));

                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(objOnGoingSwap.customer).child("rcount").setValue(rcount+1);

                                Toast.makeText(Ratings.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                                //setContentView(R.layout.activity_home);
                                startActivity(new Intent(getApplicationContext(), UserMenu.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Ratings.this, "Tansaction Failed", Toast.LENGTH_SHORT).show();
                        //setContentView(R.layout.activity_home);
                        startActivity(new Intent(getApplicationContext(), UserMenu.class));
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Ratings.this, "Rating dismissed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

}