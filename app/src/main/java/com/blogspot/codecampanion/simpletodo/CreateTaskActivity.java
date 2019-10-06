package com.blogspot.codecampanion.simpletodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
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

    private ToDoViewModel viewModel;
    private int taskId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        task = findViewById(R.id.editTextTask);
        RecyclerView recyclerView = findViewById(R.id.subTaskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SubTaskAdapter adapter = new SubTaskAdapter();
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
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Task");
            taskId = intent.getIntExtra(EXTRA_ID, -1);
            task.setText(intent.getStringExtra(EXTRA_TASK));
        } else {
            setTitle("Add Task");
        }

        viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        viewModel.getAllSubTasks(taskId).observe(this, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(List<SubTask> subTasks) {
                //recyclerView
                adapter.setSubTaskList(subTasks);
            }
        });

        adapter.setListener(new SubTaskAdapter.SetOnItemClickListener() {
            @Override
            public void OnItemClick(SubTask subTask) {
                BottomSheetSubTask bottomSheetSubTask = new BottomSheetSubTask();
                Bundle bundle = new Bundle();
                bundle.putString("SUB_TASK", subTask.getSubTask());
                bundle.putInt("SUB_ID", subTask.getId());
                bottomSheetSubTask.setArguments(bundle);
                bottomSheetSubTask.show(getSupportFragmentManager(), "Sub Task BottomSheet");
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
                    viewModel.deleteSubTask(adapter.getSubTask(viewHolder.getAdapterPosition()));
                Toast.makeText(CreateTaskActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

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
        if (task.trim().isEmpty()) {
            Toast.makeText(this, "Enter A task", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
            data.putExtra(EXTRA_TASK, task);
            setResult(RESULT_OK, data);
        }

        finish();

    }

    @Override
    public void onSaveClicked(String string, int subTaskID) {
        if (subTaskID != -1) {
            SubTask subTask = new SubTask(string, taskId);
            subTask.setId(subTaskID);
            viewModel.updateSubTask(subTask);
        } else {
            viewModel.insertSubTask(new SubTask(string, taskId));
        }
    }
}
