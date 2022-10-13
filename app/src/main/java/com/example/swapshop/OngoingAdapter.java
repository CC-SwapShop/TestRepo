package com.example.swapshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.OngoingAdapterHolder> {

    //Variables
    private Context mContext;
    private List<OnGoingSwaps> mUploads;
    private OnItemClickListener mListener;

    //Constructor
    public OngoingAdapter(Context context, List<OnGoingSwaps> uploads){
        mContext = context;
        mUploads = uploads;
    }

    //Adapter
    @NonNull
    @Override
    public OngoingAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating view
        View view = LayoutInflater.from(mContext).inflate(R.layout.ongoing_item,parent,false);
        return new OngoingAdapterHolder(view);
    }

    //Bind
    @Override
    public void onBindViewHolder(@NonNull OngoingAdapterHolder holder, int position) {
        //Position of onGoingSwap
        OnGoingSwaps onGoingSwaps = mUploads.get(position);

        //Database reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products").child(onGoingSwaps.productId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.txtCvProduct.setText("Product Name: "+snapshot.child("name").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Database reference
        DatabaseReference refs = FirebaseDatabase.getInstance().getReference().child("Users").child(onGoingSwaps.provider);
        refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.txtCvProvider.setText("Product owner: "+snapshot.child("name").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Database reference
        DatabaseReference refc = FirebaseDatabase.getInstance().getReference().child("Users").child(onGoingSwaps.customer);
        refc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.txtCvCustomer.setText("Customer: "+snapshot.child("name").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Item count function
    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    //Class
    public class  OngoingAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtCvProvider, txtCvCustomer,txtCvProduct;

        public OngoingAdapterHolder(@NonNull View itemView) {
            super(itemView);

            //Item views
            txtCvCustomer = itemView.findViewById(R.id.txtCvCustomer);
            txtCvProduct = itemView.findViewById(R.id.txtCvProduct);
            txtCvProvider = itemView.findViewById(R.id.txtCvProvider);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
