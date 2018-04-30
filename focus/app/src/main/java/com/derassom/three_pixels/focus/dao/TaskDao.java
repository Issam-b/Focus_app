package com.derassom.three_pixels.focus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE task_name like:name")
    Task getTask(String name);

    @Query("SELECT task_name FROM task")
    List<String> getTaskName();
    @Query("SELECT * FROM task WHERE task_selected LIKE:state")
    List<Task> getSelectedTasks(boolean state);

    @Query("SELECT * FROM task WHERE task_complete LIKE:state")
    List<Task> getCompletedTasks(boolean state);

    @Query("SELECT COUNT(*) from task")
    int countTasks();

    @Insert
    void insert(Task task);
    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);
    @Update
    void update(Task task);

}
