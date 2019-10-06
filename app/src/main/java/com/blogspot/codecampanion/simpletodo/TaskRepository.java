package com.blogspot.codecampanion.simpletodo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private SubTaskDao subTaskDao;
    private LiveData<List<Task>> taskList;


    private LiveData<List<SubTask>> subTaskList;

    TaskRepository(Application application){
        TaskDatabase taskDatabase = TaskDatabase.getInstance(application);
        taskDao = taskDatabase.taskDao();
        subTaskDao = taskDatabase.subTaskDao();
    }

    void insert(Task task){
        new InsertAsyncTask(taskDao).execute(task);
    }

    void update(Task task){
        new UpdateAsyncTask(taskDao).execute(task);
    }

    void delete(Task task){
        new DeleteAsyncTask(taskDao).execute(task);
    }

    void deleteAll(){
        new DeleteAllAsyncTask(taskDao).execute();
    }

    LiveData<List<Task>> getTaskList() {

        taskList = taskDao.getAll();
        return taskList;
    }

    public void insertSubTask(SubTask subTask){
        new InsertSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    public void updateSubTask(SubTask subTask){
        new UpdateSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    public void deleteSubTask(SubTask subTask){
        new DeleteSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    public LiveData<List<SubTask>> getSubTaskList(int taskId) {
        subTaskList =  subTaskDao.getSubTasks(taskId);
        return subTaskList;
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

    private static class InsertSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void>{

        private SubTaskDao subTaskDao;

        InsertSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.insert(subTasks[0]);
            return null;
        }
    }

    private static class UpdateSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void>{

        private SubTaskDao subTaskDao;

        UpdateSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.update(subTasks[0]);
            return null;
        }
    }

    private static class DeleteSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void>{

        private SubTaskDao subTaskDao;

        DeleteSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.delete(subTasks[0]);
            return null;
        }
    }




}
