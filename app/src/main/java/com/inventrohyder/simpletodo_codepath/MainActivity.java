package com.inventrohyder.simpletodo_codepath;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

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

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                mItems.remove(position);
                // Notify the adapter
                mItemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), R.string.item_removed, Toast.LENGTH_SHORT).show();
                saveItems();
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
                saveItems();
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // Load items by reading every line of the data file
    private void loadItems() {
        try {
            mItems = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e(TAG, "loadItems: Error reading items", e);
            mItems = new ArrayList<>();
        }
    }

    // Saves items by writing them into the data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), mItems);
        } catch (IOException e) {
            Log.e(TAG, "saveItems: Error writing items", e);
        }
    }
}