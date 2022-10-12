package com.example.swapshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.OngoingAdapterHolder> {
    private Context mContext;
    private List<OnGoingSwaps> mUploads;

    private OnItemClickListener mListener;

    public OngoingAdapter(Context context, List<OnGoingSwaps> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public OngoingAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ongoing_item,parent,false);
        return new OngoingAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingAdapterHolder holder, int position) {
        OnGoingSwaps onGoingSwaps = mUploads.get(position);
        holder.txtCvProvider.setText(onGoingSwaps.provider);
        holder.txtCvProduct.setText(onGoingSwaps.productId);
        holder.txtCvCustomer.setText(onGoingSwaps.customer);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class  OngoingAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtCvProvider, txtCvCustomer,txtCvProduct;

        public OngoingAdapterHolder(@NonNull View itemView) {
            super(itemView);

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
