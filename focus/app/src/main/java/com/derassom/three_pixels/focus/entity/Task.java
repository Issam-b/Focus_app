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

    @ColumnInfo(name = "task_type")
    private String taskType;

    @ColumnInfo(name = "task_complete")
    private Boolean taskComplete;

    @ColumnInfo(name = "task_start_time")
    private int taskStartTime;

    @ColumnInfo(name = "task_end_time")
    private int taskEndTime;

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

    public String getTaskType() {
        return taskType;
    }
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Boolean getTaskComplete() {
        return taskComplete;
    }
    public void setTaskComplete(Boolean taskComplete) {
        this.taskComplete = taskComplete;
    }

    public int getTaskStartTime() {
        return taskStartTime;
    }
    public void setTaskStartTime(int taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public int getTaskEndTime() {
        return taskEndTime;
    }
    public void setTaskEndTime(int taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

}
