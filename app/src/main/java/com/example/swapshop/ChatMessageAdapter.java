package com.example.swapshop;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessageHolder> {
    //Variables
    private Context mContext;
    private List<ChatMessage> mChatMessages;
    private String mCurrUser;
    private int mFromColor, mToColor;

    public ChatMessageAdapter(Context context, List<ChatMessage> ChatMessage, String currUser){
        mContext = context;
        mChatMessages = ChatMessage;
        mCurrUser = currUser;
        mFromColor = Color.parseColor("#165bc6");
        mToColor = Color.GRAY;
    }

    @NonNull
    @Override
    public ChatMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item,parent
                ,false);
        return new ChatMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageHolder holder, int position) {
        ChatMessage objCurrChatMessage = mChatMessages.get(position);
        holder.txtMessage.setText(objCurrChatMessage.message);
        holder.txtMessage.setTextColor(Color.WHITE);
        if(mCurrUser.equals(objCurrChatMessage.messageFrom)){
            holder.txtMessage.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.CardViewMessage.setCardBackgroundColor(mFromColor);
        }else{
            holder.CardViewMessage.setCardBackgroundColor(mToColor);
        }
    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    public class ChatMessageHolder extends RecyclerView.ViewHolder{
        public TextView txtMessage;
        public CardView CardViewMessage;

        public ChatMessageHolder(@NonNull View itemView) {
            super(itemView);

            txtMessage = itemView.findViewById(R.id.txtCVMessage);
            CardViewMessage = itemView.findViewById(R.id.CardViewMessage);
        }
    }


}
