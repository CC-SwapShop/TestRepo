package com.example.swapshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    //Variables for class Register
    private FirebaseAuth mAuth;
    private EditText edtFName, edtLName, edtEmail,edtPassword;
    private Button btnRegister;


    public String unitTest = "True";
    //onCreate method Register class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Using activity_register xml
        setContentView(R.layout.activity_register);

        //Firebase variable
        mAuth = FirebaseAuth.getInstance();

        //Views
        edtFName = (EditText) findViewById(R.id.edtRName);
        edtEmail = (EditText) findViewById(R.id.edtREmail);
        edtPassword = (EditText) findViewById(R.id.edtRPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }


    //onClick method
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnRegister:

                //Register user into database
                registerUser();

                break;
        }
    }

    //Views
    public void registerUser(){
        String name = edtFName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        //Error and validation
        if(name.isEmpty()){
            edtFName.setError("First name is required ");
            edtFName.requestFocus();
            return;
        }

        //Error if nothing is inputted
        if(email.isEmpty()){
            edtEmail.setError("Email is required ");
            edtEmail.requestFocus();
            return;
        }

        //Email has to contain @email
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Valid Email is required ");
            edtEmail.requestFocus();
            return;
        }

        //Error if nothing is inputted
        if(password.isEmpty()){
            edtPassword.setError("Password is required ");
            edtPassword.requestFocus();
            return;
        }

        //Error if password is less than 6 characters
        if(password.length()<6){
            edtPassword.setError("Password must be at least 6 charters long");
            edtPassword.requestFocus();
            return;
        }
        //Creating username and password into database
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Registering new user
                            User user = new User(name,email);

                            //Firebase connection
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            //If user successfully registered
                                            if (task.isSuccessful()){
                                                Toast.makeText(Register.this,"Successful",Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), Home.class));

                                            }
                                            else{
                                                Toast.makeText(Register.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                                                unitTest = "False";
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}