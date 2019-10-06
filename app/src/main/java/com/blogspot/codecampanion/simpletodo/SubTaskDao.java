package com.blogspot.codecampanion.simpletodo;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubTaskDao {

    @Insert
    void insert(SubTask subTask);

    @Update
    void update(SubTask subTask);

    @Delete
    void delete(SubTask subTask);

    @Query("SELECT * FROM subTaskTable")
    LiveData<List<SubTask>> getAllSubTasks();

    @Query("SELECT * FROM subTaskTable WHERE taskId=:idTask")
    LiveData<List<SubTask>> getSubTasks(int idTask);


}
