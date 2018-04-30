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

    @ColumnInfo(name = "task_selected")
    private boolean selectedTask;

    @ColumnInfo(name = "task_complete")
    private boolean taskComplete;

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

    public boolean getSelectedTask() {
        return selectedTask;
    }
    public void setSelectedTask(boolean selectedTask) {
        this.selectedTask = selectedTask;
    }

    public Boolean getTaskComplete() {
        return taskComplete;
    }
    public void setTaskComplete(boolean taskComplete) {
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
    @Override
    public String toString()
    {
        StringBuffer buffer= new StringBuffer();
        buffer.append(this.taskid);
        buffer.append(" ");
        buffer.append(this.taskName);
        buffer.append(" ");
        buffer.append(this.taskComplete);
        buffer.append(" ");
        buffer.append(this.selectedTask);
        buffer.append(" ");
        return buffer.toString();
    }
}
