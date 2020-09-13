package com.inventrohyder.simpletodo_codepath;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    final String TAG = getClass().getSimpleName();

    List<String> mItems;

    Button mBtnAdd;
    EditText mEtItem;
    RecyclerView mRvItems;
    private ItemsAdapter mItemsAdapter;

    // handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            // Retrieve the updated text value
            String itemText = Objects.requireNonNull(data).getStringExtra(KEY_ITEM_TEXT);
            // extract the original position of the edited item from the position key
            int position = Objects.requireNonNull(data.getExtras()).getInt(KEY_ITEM_POSITION);

            // update the model at the right position with the new item
            mItems.set(position, itemText);
            // notify the adapter
            mItemsAdapter.notifyItemChanged(position);
            // persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Log.w(TAG, "onActivityResult: Unknown call to onActivityResult");
        }
    }

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

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d(TAG, "onItemClicked: Single click at position " + position);
                // Create a new Activity
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                // Pass the relevant data being edited
                i.putExtra(KEY_ITEM_TEXT, mItems.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                // display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };


        mItemsAdapter = new ItemsAdapter(mItems, onLongClickListener, onClickListener);
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