package com.derassom.three_pixels.focus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.derassom.three_pixels.focus.entity.App;

import java.util.List;

@Dao
public interface AppDao {

    @Query("SELECT pkg_name FROM app")
    List<String> getPackageName();

    @Query("SELECT app_duration FROM app")
    List<Long> getAppsDuration();
    @Query("SELECT app_name FROM app")
    List<String>getAppsName();

    @Query("SELECT * FROM app")
    List<App> getAll();

    @Query("SELECT * FROM app  WHERE app_name LIKE:name")

    App getApp(String name);
    @Query("SELECT * FROM app  WHERE pkg_name LIKE:name")

    App getAppWithPkgName(String name);

    @Query("SELECT COUNT(*) from app ")
    int countApps();

    @Insert
    void insertAll(App... apps);
    @Insert
    void insert(App app);

    @Delete
    void delete(App app);

    @Update
    void update(App app);
}
