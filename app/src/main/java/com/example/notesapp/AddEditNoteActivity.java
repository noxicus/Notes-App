package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.notesapp.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.notesapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.notesapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.notesapp.EXTRA_PRIORITY";
    private static final int RESULT_CODE = 1;

    public TextView titleTextView;
    public TextView descriptionTextView;
    public NumberPicker priorityNumberPicker;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleTextView = findViewById(R.id.edit_text_title);
        descriptionTextView = findViewById(R.id.edit_text_description);
        priorityNumberPicker = findViewById(R.id.number_picker_priority);

        // Set numberPicker min and max value
        priorityNumberPicker.setMinValue(0);
        priorityNumberPicker.setMaxValue(10);

        // Set close icon on Action bar instead Up indicator (Add note activity)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // Get intent for edit functionality
        Intent editIntent = getIntent();
        if (editIntent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            titleTextView.setText(editIntent.getStringExtra(EXTRA_TITLE));
            descriptionTextView.setText(editIntent.getStringExtra(EXTRA_DESCRIPTION));
            priorityNumberPicker.setValue(editIntent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }

    }

    // Function that is called when save button is clicked
    public void saveData() {

        // Get values from text view and picker
        String title = titleTextView.getText().toString();
        String description = descriptionTextView.getText().toString();
        int priority = priorityNumberPicker.getValue();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter title and description", Toast.LENGTH_SHORT).show();
        } else {

            // Create intent and stored values
            // Intent will be received in main activity with all stored values
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_DESCRIPTION, description);
            intent.putExtra(EXTRA_PRIORITY, priority);
            int id = getIntent().getIntExtra(EXTRA_ID, -1);

            // Check if contains id so it can be sent to main activity
            if (id != -1){
                intent.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_CODE, intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu that is created
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_save:
                saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}