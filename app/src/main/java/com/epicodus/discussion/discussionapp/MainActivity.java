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



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mAddCategoryReference;
    private DatabaseReference mCategoryReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.newCategoryButton) Button mNewCategoryButton;
    @Bind(R.id.editCategoryText) EditText mEditCategoryText;
    @Bind(R.id.recyclerView)  RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAddCategoryReference = FirebaseDatabase.getInstance().getReference().child("newCategory");

        mCategoryReference = FirebaseDatabase.getInstance().getReference("newCategory");
        setUpFirebaseAdapter();


//        mAddCategoryReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
//                    String category = categorySnapshot.getValue().toString();
//                    Log.d("Categories updated", "Category: "+category);
//                    categories.add(snapshot.getValue(Category.class));
//                }
//
//                int itemPosition = getLayoutPosition();
//
//                Intent intent = new Intent(mContext, CategoryActivity.class);
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("categories", Parcels.wrap(categories));
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        mNewCategoryButton.setOnClickListener(this);
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Category, FirebaseCategoryViewHolder>
                (Category.class, R.layout.category_list_view, FirebaseCategoryViewHolder.class,
                        mCategoryReference) {

            @Override
            protected void populateViewHolder(FirebaseCategoryViewHolder viewHolder,
                                              Category model, int position) {
                viewHolder.bindCategory(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v == mNewCategoryButton) {
            String newCategory = mEditCategoryText.getText().toString();

            saveCategoryToFirebase(newCategory);

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("newCategory", newCategory);
            startActivity(intent);
        }
    }
    public void saveCategoryToFirebase(String newCategoryName) {
        Category newCategory = new Category(newCategoryName);
        mAddCategoryReference.push().setValue(newCategory);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mAddCategoryReference.removeEventListener(mAddCategoryReferenceListener);
        mFirebaseAdapter.cleanup();
    }
}
