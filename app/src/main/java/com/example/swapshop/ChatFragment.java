package com.example.swapshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    //Variables
    private RecyclerView mRecyclerView;
    private OngoingAdapter mAdapter;
    private DatabaseReference reference;
    private DatabaseReference wReference;
    private List<Product> mUploads;
    private List<String> productIDs;
    private List<String> onGoingSwapsIDs;
    private List<OnGoingSwaps> onGoingSwaps;
    private List<Product> arrproducts;
    private TextView txtCFNoItems;

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
        txtCFNoItems = view.findViewById(R.id.txtFCNoItems);
        arrproducts = new ArrayList<>();
        productIDs = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUploads = new ArrayList<>();
        onGoingSwapsIDs = new ArrayList<>();
        onGoingSwaps = new ArrayList<>();
        wReference = FirebaseDatabase.getInstance().getReference().child("ProductOngoingSwaps");
        wReference.addValueEventListener(new ValueEventListener() {
            //From database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onGoingSwaps.clear();
                onGoingSwapsIDs.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    String customer = postsnapshot.child("customer").getValue().toString();
                    String productId = postsnapshot.child("productId").getValue().toString();
                    String provider = postsnapshot.child("provider").getValue().toString();
                    boolean ongoing = (boolean) postsnapshot.child("ongoing").getValue();
                    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    //Ongoing chat reference
                    OnGoingSwaps objOnGoingSwaps = new OnGoingSwaps(customer,provider,productId,ongoing);
                    if (ongoing == true ){
                        if(user.equals(customer)||user.equals(provider)){

                            onGoingSwaps.add(objOnGoingSwaps);
                            onGoingSwapsIDs.add(postsnapshot.getKey());

                        }

                    }

                }
                //If the chat is empty
                if(onGoingSwapsIDs.isEmpty()){
                    txtCFNoItems.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }else {
                    //Setting Visibility
                    txtCFNoItems.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    //Adapter
                    mAdapter = new OngoingAdapter(getActivity(), onGoingSwaps);

                    //Starting chat2 with parsing
                    mAdapter.setOnItemClickListener(new OngoingAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            OnGoingSwaps currOnGoingSwaps = onGoingSwaps.get(position);
                            String key = onGoingSwapsIDs.get(position);
                            String sPID = currOnGoingSwaps.productId;

                            Intent intent = new Intent( getContext(), ChatActivity.class);
                            intent.putExtra("Select_ID",sPID);
                            intent.putExtra("Extra_ongoingID",key);
                            intent.putExtra("Extra_ongoing",currOnGoingSwaps);

                            startActivity(intent);

                        }
                    });

                    mRecyclerView.setAdapter(mAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}