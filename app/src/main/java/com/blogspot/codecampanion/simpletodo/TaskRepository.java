package com.blogspot.codecampanion.simpletodo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> taskList;

    public TaskRepository(Application application){
        TaskDatabase taskDatabase = TaskDatabase.getInstance(application);
        taskDao = taskDatabase.taskDao();
        taskList = taskDao.getAll();
    }

    public void insert(Task task){
        new InsertAsyncTask(taskDao).execute(task);
    }

    public void update(Task task){
        new UpdateAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task){
        new DeleteAsyncTask(taskDao).execute(task);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(taskDao).execute();
    }

    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }


    private static class InsertAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        InsertAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        UpdateAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        DeleteAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private TaskDao taskDao;

        DeleteAllAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAll();
            return null;
        }
    }


}
