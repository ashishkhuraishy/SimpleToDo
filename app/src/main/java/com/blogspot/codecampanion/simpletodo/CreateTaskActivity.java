package com.blogspot.codecampanion.simpletodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class CreateTaskActivity extends AppCompatActivity implements BottomSheetSubTask.BottomSheetListner {

    public static final String EXTRA_ID = "com.blogspot.codecampanion.simpletodo.EXTRA_ID";
    public static final String EXTRA_TASK = "com.blogspot.codecampanion.simpletodo.EXTRA_TASK";

    private TextInputEditText task;
    private TextView tempText;

    private ToDoViewModel viewModel;
    private int taskId  = -1;

    private SubTaskAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        task = findViewById(R.id.editTextTask);
        RecyclerView recyclerView = findViewById(R.id.subTaskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        Button addSubTaskBtn = findViewById(R.id.addSubTaskBTN);
        addSubTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetSubTask bottomSheetSubTask = new BottomSheetSubTask();
                bottomSheetSubTask.show(getSupportFragmentManager(), "Sub Task BottomSheet");
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Task");
            taskId = intent.getIntExtra(EXTRA_ID, -1);
            task.setText(intent.getStringExtra(EXTRA_TASK));
        }else {
            setTitle("Add Task");
            task.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if(i == EditorInfo.IME_ACTION_DONE){
                        AddTask();
                        return true;
                    }

                    return false;
                }
            });
        }

        viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        if(taskId != -1) {
            viewModel.getAllSubTasks(taskId).observe(this, new Observer<List<SubTask>>() {
                @Override
                public void onChanged(List<SubTask> subTasks) {
                    //recyclerView
                    adapter.setSubTaskList(subTasks);
                }
            });
        }

    }

    private void AddTask() {
        String task = this.task.getText().toString();
        Task addTask = new Task(task);
        taskId = addTask.getId();
        viewModel.insert(addTask);

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
            data.putExtra(EXTRA_TASK, task);
            setResult(RESULT_OK, data);
        }

        finish();

    }

    @Override
    public void onSaveClicked(String string) {
        viewModel.insertSubTask(new SubTask(string, taskId));
    }
}
