package com.inventrohyder.simpletodo_codepath;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> mItems;

    Button mBtnAdd;
    EditText mEtItem;
    RecyclerView mRvItems;
    private ItemsAdapter mItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnAdd = findViewById(R.id.btnAdd);
        mEtItem = findViewById(R.id.etItem);
        mRvItems = findViewById(R.id.rv_items);

        mItems = new ArrayList<>();
        mItems.add("Buy milk");
        mItems.add("Go to the gym");
        mItems.add("Have fun!");

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                mItems.remove(position);
                // Notify the adapter
                mItemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), R.string.item_removed, Toast.LENGTH_SHORT).show();
            }
        };
        mItemsAdapter = new ItemsAdapter(mItems, onLongClickListener);
        mRvItems.setAdapter(mItemsAdapter);
        mRvItems.setLayoutManager(new LinearLayoutManager(this));

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = mEtItem.getText().toString();
                // Add item to the model
                mItems.add(todoItem);
                // Notify adapter that an item is inserted
                mItemsAdapter.notifyItemInserted(mItems.size() - 1);
                mEtItem.setText("");
                Toast.makeText(getApplicationContext(), R.string.item_added, Toast.LENGTH_SHORT).show();
            }
        });
    }
}