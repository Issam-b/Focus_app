package com.derassom.three_pixels.focus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT COUNT(*) from task")
    int countTasks();

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(App task);
}
