package com.epicodus.discussion.discussionapp;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseMessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseMessageViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindMessage(Message message) {

        TextView messageTextView = (TextView) mView.findViewById(R.id.messageTextView);

        messageTextView.setText(message.getName());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Message> categories = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("messages");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    categories.add(snapshot.getValue(Message.class));
                }

                int itemPosition = getLayoutPosition();

//                Intent intent = new Intent(mContext, MessageActivity.class);
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("categories", Parcels.wrap(categories));

//                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}