package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserInfoFragment extends Fragment {
    TextView txtUI_name;
    Button btnSignOut;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        //find view components
        txtUI_name = view.findViewById(R.id.txtUI_name);
        btnSignOut = view.findViewById(R.id.btnSignout);

        //initialise values
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference refs = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtUI_name.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUserOut();
            }
        });

        return view;
    }

    public void SignUserOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), Home.class));
    }
}