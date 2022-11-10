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

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    //Variables
    private Context mContext;
    private List<Product> mUploads;
    private OnItemClickListener mListener;

    //Constructor
    public ImageAdapter(Context context, List<Product> uploads){
        mContext = context;
        mUploads = uploads;
    }

    //Inflate
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    //Binding
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Product currProduct = mUploads.get(position);
        holder.textViewName.setText(currProduct.name);
        holder.textViewW.setText("           "+ currProduct.reqProduct);
        Picasso.with(mContext)
                .load(currProduct.img)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    //item count
    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    //Image holder
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName,textViewW;
        public ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name1);
            textViewW = itemView.findViewById(R.id.textViewR1);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
        }

        //Positions
        @Override
        public void onClick(View v){
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        /*@Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem AddWishlist = contextMenu.add(Menu.NONE,1,1,"Add to Wishlist");
            MenuItem Swap = contextMenu.add(Menu.NONE,2,2,"Swap");

            AddWishlist.setOnMenuItemClickListener(this);
            Swap.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onWishlistClick(position);
                        case 2:
                            mListener.onSwapped(position);
                    }
                }
            }
            return false;
        }*/
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

        //void onWishlistClick(int position);

        //void onSwapped(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
