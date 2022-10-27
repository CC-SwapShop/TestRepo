package com.example.swapshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoHolder> {
    private Context mContext;
    private List<Product> mUploads;

    public UserInfoAdapter(Context context, List<Product> uploads){
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public UserInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.userproduct_item,parent,false);
        return new UserInfoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoHolder holder, int position) {
        Product currProduct = mUploads.get(position);
        holder.txtName.setText(currProduct.name);
        Picasso.with(mContext)
                .load(currProduct.img)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class UserInfoHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView txtName;

        public UserInfoHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.UICV_product_image);
            txtName = itemView.findViewById(R.id.UICV_product_title);

        }


    }



}
