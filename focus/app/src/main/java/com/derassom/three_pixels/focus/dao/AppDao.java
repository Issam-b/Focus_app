package com.derassom.three_pixels.focus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.derassom.three_pixels.focus.entity.App;

import java.util.List;

@Dao
public interface AppDao {

    @Query("SELECT * FROM app")
    List<App> getAll();

    @Query("SELECT COUNT(*) from app")
    int countApps();

    @Insert
    void insertAll(App... apps);

    @Delete
    void delete(App app);
}
