package com.inventrohyder.simpletodo_codepath;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button mBtnAdd;
    EditText mEtItem;
    RecyclerView mRvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnAdd = findViewById(R.id.btnAdd);
        mEtItem = findViewById(R.id.etItem);
        mRvItems = findViewById(R.id.rv_items);

        items = new ArrayList<>();
        items.add("Buy milk");
        items.add("Go to the gym");
        items.add("Have fun!");

        ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        mRvItems.setAdapter(itemsAdapter);
        mRvItems.setLayoutManager(new LinearLayoutManager(this));
    }
}