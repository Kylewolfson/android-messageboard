package com.epicodus.discussion.discussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mAddCategoryReference;

    private ValueEventListener mAddCategoryReferenceListener;

    @Bind(R.id.newCategoryButton) Button mNewCategoryButton;
    @Bind(R.id.editCategoryText) EditText mEditCategoryText;
    @Bind(R.id.listView) ListView listView;
    @Bind(R.id.recyclerView)  RecyclerView mRecyclerView;


    private String[] categories = new String[] {"Local", "News",
            "Events", "Videogames", "Dating", "Food", "Advice", "Coding"};

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAddCategoryReference = FirebaseDatabase.getInstance().getReference().child("newCategory");

        mAddCategoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String category = categorySnapshot.getValue().toString();
                    Log.d("Categories updated", "Category: "+category);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

//        getCategories(category);


        mListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        mListView.setAdapter(adapter);
        mNewCategoryButton.setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                intent.putExtra("category", ((TextView)view).getText().toString());
                startActivity(intent);
            }
        });
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
        mAddCategoryReference.removeEventListener(mAddCategoryReferenceListener);
    }
}
