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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class UploadProduct extends AppCompatActivity {
    //Variables for uploading product to firebase
    TextView edtAName, edtADecription, edtALocation, edtAreqProduct;
    ImageView imgSwap;
    Button btnAPAdd;
    Uri imgUri;

    String unitTest = "True";

    FirebaseStorage storage;
    StorageReference storageReference;
    private final int IMG_REQUEST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        //Finding text views
        edtAName = findViewById(R.id.edtAName);
        edtADecription = findViewById(R.id.edtADescription);
        edtALocation = findViewById(R.id.edtALocation);
        edtAreqProduct = findViewById(R.id.edtALreqProduct);
        imgSwap = findViewById(R.id.imgSwap);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imgSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        btnAPAdd = findViewById(R.id.btnAUpload);
        btnAPAdd.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewProduct();
            }
        });

    }

    public void AddNewProduct(){
        //Getting variables
        String name = edtAName.getText().toString().trim();
        String description = edtADecription.getText().toString().trim();
        String location = edtALocation.getText().toString().trim();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String sReqProduct = edtAreqProduct.getText().toString().trim();

        if (imgUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();

            StorageReference reference = storageReference.child("Item_Images/"+ UUID.randomUUID().toString());

            try {
                reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //Uploading to database
                                Product product = new Product(name,description,location,sReqProduct, uri.toString(),UID,"");
                                product.setStatusAvailable();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");
                                String key = ref.push().getKey();
                                ref.child(key).setValue(product);

                                progressDialog.dismiss();

                                Toast.makeText(UploadProduct.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                //setContentView(R.layout.activity_home);
                                startActivity(new Intent(getApplicationContext(), UserMenu.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UploadProduct.this, "Error has occured" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                unitTest = "False";
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadProduct.this, "Error has occured" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        unitTest = "False";
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double Progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Saving");

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),IMG_REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST_ID && resultCode==RESULT_OK && data!=null && data.getData() != null){
            imgUri = data.getData();

            try {
                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imgSwap.setImageBitmap(bitmapImg);
                btnAPAdd.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}