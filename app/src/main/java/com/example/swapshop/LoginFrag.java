package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFrag extends Fragment {

    static boolean test= false;

    public LoginFrag() {
        // Required empty public constructor
    }

    TextView vw;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        EditText edtLEmail,edtLPassword;
        Button btnLRegister;
        Button btnLogin;
        FirebaseAuth fAuth;
        btnLRegister = view.findViewById(R.id.btnLRegister);
        edtLEmail = view.findViewById(R.id.edtLEmail);
        edtLPassword = view.findViewById(R.id.edtLPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtLEmail.getText().toString().trim();
                String pass = edtLPassword.getText().toString().trim();
                //error messages
                if (TextUtils.isEmpty(email)) {
                    edtLEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edtLPassword.setError("Password is Required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Redirect to menu
                            Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
                            //TODO: startActivity(new Intent(getContext(), UserMenu.class));
                            //finish();
                        } else {
                            //Error message
                            Toast.makeText(getActivity(), "Error occurred!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getActivity(), Register.class));
            }
        });

        return view;
    }
}