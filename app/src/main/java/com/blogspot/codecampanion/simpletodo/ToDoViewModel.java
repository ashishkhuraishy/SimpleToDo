package com.blogspot.codecampanion.simpletodo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {


    private TaskRepository taskRepository;
    private LiveData<List<Task>> data;


    public ToDoViewModel(@NonNull Application application) {
        super(application);

        taskRepository = new TaskRepository(application);
        data = taskRepository.getTaskList();
    }


    public void insert(Task task){
        taskRepository.insert(task);
    }

    public void update(Task task){
        taskRepository.update(task);
    }

    public void delete(Task task){
        taskRepository.delete(task);
    }

    public void deleteAll(){
        taskRepository.deleteAll();
    }


    public LiveData<List<Task>> getData() {
        return data;
    }


    void insertSubTask(SubTask subTask){
        taskRepository.insertSubTask(subTask);
    }

    void updateSubTask(SubTask subTask){
        taskRepository.updateSubTask(subTask);
    }

    void deleteSubTask(SubTask subTask){
        taskRepository.deleteSubTask(subTask);
    }

    LiveData<List<SubTask>> getAllSubTasks(int id){
        return taskRepository.getSubTaskList(id);
    }
}
