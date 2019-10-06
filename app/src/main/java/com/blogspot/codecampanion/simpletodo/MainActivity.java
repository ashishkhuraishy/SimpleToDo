package com.blogspot.codecampanion.simpletodo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int ADD_TASK_REQUEST_CODE = 1;
    private int EDIT_REQUEST_CODE = 2;


    private ToDoViewModel toDoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OnClick
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });


        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        toDoViewModel.getData().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                //RecyclerView
                adapter.submitList(tasks);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                toDoViewModel.delete(adapter.getTask(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted Item", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Task task) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                intent.putExtra(CreateTaskActivity.EXTRA_ID, task.getId());
                intent.putExtra(CreateTaskActivity.EXTRA_TASK, task.getTask());
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK){
            Task task = new Task(data.getStringExtra(CreateTaskActivity.EXTRA_TASK));
            int id = data.getIntExtra(CreateTaskActivity.EXTRA_ID, -1);
            if(id != -1) {
                task.setId(id);
                toDoViewModel.update(task);
                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Oops Something Went wrong!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Oops Something Went wrong!", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
