package com.derassom.three_pixels.focus.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int taskid;

    @ColumnInfo(name = "task_name")
    private String taskName;

    @ColumnInfo(name = "task_complete")
    private Boolean taskComplete;

    @ColumnInfo(name = "task_time")
    private int taskTime;


    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public Boolean getTaskComplete() {
        return taskComplete;
    }

    public void setTaskComplete(Boolean taskComplete) {
        this.taskComplete = taskComplete;
    }


    public int getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }

}
