package com.inventrohyder.simpletodo_codepath;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    private EditText mEtItem;
    private Button mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mEtItem = findViewById(R.id.etItem);
        mBtnSave = findViewById(R.id.btnSave);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.edit_item);

        mEtItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        // When the user is done editing, they click the save button
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent which will contain the results of what the user is modifying
                Intent intent = new Intent();
                // pass the data results of editing
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, mEtItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, Objects.requireNonNull(getIntent().getExtras()).getInt(MainActivity.KEY_ITEM_POSITION));

                //set the result of the intent
                setResult(RESULT_OK, intent);

                // finish activity, close the screen and go back
                finish();
            }
        });
    }
}