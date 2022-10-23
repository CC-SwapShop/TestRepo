package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class update_details_activity extends AppCompatActivity {
    private EditText edtFName,edtsemail;
    Button btnchguname,btnsendemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        edtFName =(EditText) findViewById(R.id.edit_newname);
        edtsemail  =(EditText) findViewById(R.id.edtsndemail);
        btnchguname = findViewById(R.id.btnchguname);
        btnsendemail = findViewById(R.id.btnsendemail);



        btnchguname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newuname();
            }
        });

        btnsendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendresetemail();
            }
        });

    }

     public void newuname() {
         String new_uname = edtFName.getText().toString().trim();

         //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

         FirebaseDatabase.getInstance().getReference("Users")
                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(new_uname)
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(update_details_activity.this,"Username changed",Toast.LENGTH_LONG).show();
                         }
                         else{
                             Toast.makeText(update_details_activity.this,"Error occurred",Toast.LENGTH_LONG).show();
                         }
                     }
                 });

         /*UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                 .setDisplayName(new_uname)
                 .build();

         user.updateProfile(profileUpdates)
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(update_details_activity.this,"Username changed",Toast.LENGTH_LONG).show();
                         }
                     }
                 });*/

     }

     public void sendresetemail()
     {
         FirebaseAuth auth = FirebaseAuth.getInstance();
         String emailAddress = edtsemail.getText().toString().trim();

         auth.sendPasswordResetEmail(emailAddress)
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(update_details_activity.this,"Password reset email sent",Toast.LENGTH_LONG).show();
                         }
                     }
                 });
     }


}