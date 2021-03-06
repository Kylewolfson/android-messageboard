package com.epicodus.discussion.discussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;



public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mAddMessageReference;
    private DatabaseReference mMessageReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.newMessageButton) Button mNewMessageButton;
    @Bind(R.id.editMessageText) EditText mEditMessageText;
    @Bind(R.id.recyclerView)  RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        mAddMessageReference = FirebaseDatabase.getInstance().getReference("messages");

        mMessageReference = FirebaseDatabase.getInstance().getReference("messages");
        setUpFirebaseAdapter();


        mNewMessageButton.setOnClickListener(this);
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, FirebaseMessageViewHolder>
                (Message.class, R.layout.message_list_view, FirebaseMessageViewHolder.class,
                        mMessageReference) {

            @Override
            protected void populateViewHolder(FirebaseMessageViewHolder viewHolder,
                                              Message model, int position) {
                viewHolder.bindMessage(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v == mNewMessageButton) {
            String newMessage = mEditMessageText.getText().toString();

            saveMessageToFirebase(newMessage);
        }
    }
    public void saveMessageToFirebase(String newMessageName) {
        Message newMessage = new Message(newMessageName);
        mAddMessageReference.push().setValue(newMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mAddMessageReference.removeEventListener(mAddMessageReferenceListener);
        mFirebaseAdapter.cleanup();
    }
}
