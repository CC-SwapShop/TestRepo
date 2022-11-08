package com.example.swapshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterHolder>{

    //Variables
    private Context mContext;
    private List<String> mChatMessages;

    //Constructor
    public MessageAdapter(Context context, List<String> ChatMessages){
        mContext = context;
        mChatMessages = ChatMessages;

    }

    @NonNull
    @Override
    //Inflating view
    public MessageAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item,parent
                ,false);
        return new MessageAdapterHolder(view);
    }


    //Binding
    @Override
    public void onBindViewHolder(@NonNull MessageAdapterHolder holder, int position) {
        //ChatMessage chatMessageCurrent = mChatMessages.get(position);
        //holder.txtMessage.setText(chatMessageCurrent.message);
        String currMessage = mChatMessages.get(position);
        holder.txtMessage.setText(currMessage);
    }

    //Message size
    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    //Adapter
    public class MessageAdapterHolder extends RecyclerView.ViewHolder {
        public TextView txtMessage;

        public MessageAdapterHolder(@NonNull View itemView) {
            super(itemView);

            txtMessage = itemView.findViewById(R.id.txtCVMessage);
        }
    }

}
