package com.blogspot.codecampanion.simpletodo;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Task.class, parentColumns = "id", childColumns = "taskId", onDelete = CASCADE, onUpdate = CASCADE), tableName = "subTaskTable")
public class SubTask {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String subTask;
    private int taskId;

    public SubTask(String subTask, int taskId) {
        this.subTask = subTask;
        this.taskId = taskId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public String getSubTask() {
        return subTask;
    }

    public int getTaskId() {
        return taskId;
    }
}
