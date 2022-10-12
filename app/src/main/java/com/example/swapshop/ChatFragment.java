package com.example.swapshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OngoingAdapter mAdapter;
    private DatabaseReference reference;
    private DatabaseReference wReference;

    private List<Product> mUploads;
    private List<String> productIDs;
    private List<String> onGoingSwapsIDs;
    private List<OnGoingSwaps> onGoingSwaps;
    private List<Product> arrproducts;

    //Empty public constructor
    public ChatFragment() {
        // Required empty public constructor
    }



    //Used to create the view of the fragment to be used on the menu class
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView_Ongoing);

        arrproducts = new ArrayList<>();
        productIDs = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUploads = new ArrayList<>();
        onGoingSwapsIDs = new ArrayList<>();
        onGoingSwaps = new ArrayList<>();


        wReference = FirebaseDatabase.getInstance().getReference().child("OngoingSwaps");

        wReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    String customer = postsnapshot.child("customer").getValue().toString();
                    String productId = postsnapshot.child("productId").getValue().toString();
                    String provider = postsnapshot.child("provider").getValue().toString();
                    boolean ongoing = (boolean) postsnapshot.child("ongoing").getValue();

                    OnGoingSwaps objOnGoingSwaps = new OnGoingSwaps(customer,provider,productId,ongoing);
                    if (ongoing == true){
                        onGoingSwaps.add(objOnGoingSwaps);
                        onGoingSwapsIDs.add(postsnapshot.getKey());
                    }
                }

                mAdapter = new OngoingAdapter(getActivity(), onGoingSwaps);

                mAdapter.setOnItemClickListener(new OngoingAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getContext(),"click at: " + position,Toast.LENGTH_SHORT).show();
                        OnGoingSwaps currOnGoingSwaps = onGoingSwaps.get(position);
                        String key = onGoingSwapsIDs.get(position);
                        String sPID = currOnGoingSwaps.productId;




                    }
                });

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}