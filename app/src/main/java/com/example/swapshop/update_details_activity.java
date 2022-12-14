package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class update_details_activity extends AppCompatActivity {

    //Variables
    private EditText edtFName,edtsemail;
    ImageView imgDP;
    Button btnchguname,btnsendemail, btnuploaddp;
    Uri imgUri;

    //Firebase variables
    FirebaseStorage storage;
    StorageReference storageReference;
    private final int IMG_REQUEST_ID = 1;

    //On create method for update_details_activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        //Finding views
        edtFName =(EditText) findViewById(R.id.edit_newname);
        edtsemail  =(EditText) findViewById(R.id.edtsndemail);
        btnchguname = findViewById(R.id.btnchguname);
        btnsendemail = findViewById(R.id.btnsendemail);
        btnuploaddp = findViewById(R.id.btnuploaddp);
        imgDP = findViewById(R.id.imgpicture);

        //Database variables
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //On click for new name
        btnchguname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newuname();
            }
        });

        //On click for email to send
        btnsendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendresetemail();
            }
        });

        //Onclick for display picture
        imgDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        //Onclick to upload new DP
        btnuploaddp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewProfilePicture();
            }
        });


    }

    //New user name function
    public void newuname() {

        //string to get text
        String new_uname = edtFName.getText().toString().trim();

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Firebase changing user name
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(new_uname)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //What to do if task was not success
                        if(task.isSuccessful()){
                            Toast.makeText(update_details_activity.this,"Username changed",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(update_details_activity.this,"Error occurred",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    //Method to change password
    public void sendresetemail()
    {
        //Firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = edtsemail.getText().toString().trim();


        //Checking to see if emails match as wont send if it does not
        if(emailAddress.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())==false){
            edtsemail.setError("enter yor email address");
            edtsemail.requestFocus();
            return;
        }

        //If task is succesfull
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(update_details_activity.this,"Password reset email sent",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        edtsemail.setText("");
    }

    //Method to choose picture
    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),IMG_REQUEST_ID);
    }


    //Method to store the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST_ID && resultCode==RESULT_OK && data!=null && data.getData() != null){
            imgUri = data.getData();

            try {
                //Storing
                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imgDP.setImageBitmap(bitmapImg);
                btnuploaddp.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //Adding new profile and updating
    public void AddNewProfilePicture(){
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (imgUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();

            //Database reference
            StorageReference reference = storageReference.child("ProfilePic_Images/"+ UUID.randomUUID().toString());
            try{
                //take for image
                reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //Uploading to database
                                //add a dropdown for categories
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("img").setValue(uri.toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    progressDialog.dismiss();
                                                    Toast.makeText(update_details_activity.this,"Profile picture changed",Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    Toast.makeText(update_details_activity.this,"Error occurred",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(update_details_activity.this, "Error has occured" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(update_details_activity.this, "Error has occured" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double Progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Saving");

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}