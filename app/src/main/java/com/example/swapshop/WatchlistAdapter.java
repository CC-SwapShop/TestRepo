package com.example.swapshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistHolder>{

    //Variables for watch list adapter
    private Context mContext;
    private List<Product> mUploads;
    private OnItemClickListener mListener;

    //constructor
    public WatchlistAdapter(Context context, List<Product> uploads){
        mContext = context;
        mUploads = uploads;
    }

    //Holder
    @NonNull
    @Override
    public WatchlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.watchlist_item,parent,false);
        return new WatchlistHolder(v);
    }


    //Image
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull WatchlistHolder holder, int position) {
        Product currProduct = mUploads.get(position);
        holder.textViewName.setText(currProduct.name);
        Picasso.with(mContext)
                .load(currProduct.img)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        if(currProduct.checkSwapped() == true) holder.cardView.setCardBackgroundColor(R.color.red);

    }


    //Getting item count
    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class WatchlistHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName;
        public ImageView imageView;
        public CardView cardView;

        public WatchlistHolder(@NonNull View itemView) {
            super(itemView);

            //Getting views
            textViewName = itemView.findViewById(R.id.text_view_name1);
            imageView = itemView.findViewById(R.id.image_view_upload1);
            cardView = itemView.findViewById(R.id.card_view);
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

       /* @Override
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

        /*void onWishlistClick(int position);

        void onSwapped(int position);*/
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
