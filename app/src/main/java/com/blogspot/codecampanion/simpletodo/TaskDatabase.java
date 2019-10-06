package com.blogspot.codecampanion.simpletodo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



@Database(entities = {Task.class, SubTask.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public static TaskDatabase instance;

    public abstract TaskDao taskDao();
    public abstract SubTaskDao subTaskDao();

    public static synchronized TaskDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDatabase.class, "task_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
