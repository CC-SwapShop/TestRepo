package com.example.swapshop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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


public class UploadFrag extends Fragment {

    //Defining variables
    TextView edtAName, edtADecription, edtALocation, edtAreqProduct;
    ImageView imgSwap;
    Button btnAPAdd;
    Uri imgUri;
    Spinner dropdown;
    ArrayAdapter<String> adapter;

    String item;

    //Firebase variables
    FirebaseStorage storage;
    StorageReference storageReference;
    private final int IMG_REQUEST_ID = 1;

    //Empty constructor for fragment
    public UploadFrag() {
        // Required empty public constructor
    }


    //onCreateView methof for UploadFrag
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_upload_product, container, false);

        //Finding corresponding views
        edtAName = view.findViewById(R.id.edtAName);
        edtADecription = view.findViewById(R.id.edtADescription);
        edtALocation = view.findViewById(R.id.edtALocation);
        edtAreqProduct = view.findViewById(R.id.edtALreqProduct);
        imgSwap = view.findViewById(R.id.imgSwap);

        dropdown = view.findViewById(R.id.spinner2);
        //create a list of items for the spinner.
        String[] items = new String[]{"Toys", "Home & Appliance", "Games","Sport","Other"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
//item= adapter.toString();
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                item=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });








        //Firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Image redirect to choose picture
        imgSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        //Corresponding views for product information
        btnAPAdd = view.findViewById(R.id.btnAUpload);
        btnAPAdd.setEnabled(false);

        //Add new product when clicked
        btnAPAdd.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewProduct();
            }
        });

        //returning views
        return view;
    }



    //Method to add new product
    public void AddNewProduct(){
        //Getting variables
        String name = edtAName.getText().toString().trim();
        String description = edtADecription.getText().toString().trim();
        String location = edtALocation.getText().toString().trim();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String sReqProduct = edtAreqProduct.getText().toString().trim();

        //get the spinner from the xml.


        //If image is chosen
        if (imgUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Please wait...");
            progressDialog.show();

            //reference to database
            StorageReference reference = storageReference.child("Item_Images/"+ UUID.randomUUID().toString());

            try {
                reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override

                            //If successful
                            public void onSuccess(Uri uri) {
                                //Uploading to database
                                //ToDo: add a dropdown for categories
                                Product product = new Product(name,description,location,sReqProduct, uri.toString(),UID,"",item);
                                product.setStatusAvailable();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");
                                String key = ref.push().getKey();
                                ref.child(key).setValue(product);



                                //dismiss the dialog
                                progressDialog.dismiss();

                                //If product is uploaded successfully
                                Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                                //setContentView(R.layout.activity_home);
                                startActivity(new Intent(getContext(), UserMenu.class));
                            }

                            //if failed
                        }).addOnFailureListener(new OnFailureListener() {
                            //error message
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error has occured" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    //Error occured
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error has occured" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    //Saving
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

    //Method to choose picture
    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),IMG_REQUEST_ID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST_ID && resultCode== Activity.RESULT_OK && data!=null && data.getData() != null){
            imgUri = data.getData();

            try {
                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                imgSwap.setImageBitmap(bitmapImg);
                btnAPAdd.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}