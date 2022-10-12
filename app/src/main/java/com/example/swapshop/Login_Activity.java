package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    //defining variables for class
    static boolean test= false;

    public String unitTest = "True";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        EditText edtLEmail,edtLPassword;
        Button btnLRegister;
        Button btnLogin;
        FirebaseAuth fAuth;

        //Finding corresponding items from Login xml
        btnLRegister = findViewById(R.id.btnLRegister);
        edtLEmail = findViewById(R.id.edtLEmail);
        edtLPassword = findViewById(R.id.edtLPassword);
        btnLogin = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting email and password from user input
                String email = edtLEmail.getText().toString().trim();
                String pass = edtLPassword.getText().toString().trim();

                //error messages
                //Validation for email
                if (Validation.StringEmpty(email)) {
                    edtLEmail.setError("Email is Required");
                    edtLEmail.requestFocus();
                    return;
                }
                //Validation for password
                if (Validation.StringEmpty(pass)) {
                    edtLPassword.setError("Password is Required");
                    edtLPassword.requestFocus();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If the user is in database
                        if (task.isSuccessful()) {
                            //Redirect to menu
                            Toast.makeText(Login_Activity.this, "Logged in", Toast.LENGTH_SHORT).show();

                            //Direct to registered menu
                            startActivity(new Intent(Login_Activity.this, UserMenu.class));
                            //finish();
                        } else {
                            //Error message
                            Toast.makeText(Login_Activity.this, "Error occurred!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            unitTest = "False";
                        }
                    }
                });
            }
        });
        //}
        //Register redirect
        btnLRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If user needs to register, redirect
                startActivity(new Intent(Login_Activity.this, Register.class));
            }
        });
    }
}
