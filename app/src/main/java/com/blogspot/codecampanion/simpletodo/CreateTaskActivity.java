package com.blogspot.codecampanion.simpletodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CreateTaskActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.blogspot.codecampanion.simpletodo.EXTRA_ID";
    public static final String EXTRA_TASK = "com.blogspot.codecampanion.simpletodo.EXTRA_TASK";

    private TextInputEditText task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        task = findViewById(R.id.editTextTask);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Task");
            task.setText(intent.getStringExtra(EXTRA_TASK));
        }else {
            setTitle("Add Task");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.save_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveItem:
                saveItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void saveItem() {

        Intent data = new Intent();
        String task = this.task.getText().toString();
        if(task.trim().isEmpty()){
            Toast.makeText(this, "Enter A task", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        data.putExtra(EXTRA_TASK, task);
        setResult(RESULT_OK, data);
        finish();

    }
}
