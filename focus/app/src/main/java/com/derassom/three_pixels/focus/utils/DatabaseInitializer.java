package com.derassom.three_pixels.focus.utils;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.derassom.three_pixels.focus.database.AppDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestApp(db);
        populateWithTestTask(db);
    }

    private static App addApp(final AppDatabase db, App app) {
        db.appDao().insertAll(app);
        return app;
    }

    private static void populateWithTestApp(AppDatabase db) {
        App app = new App();
        app.setAppName("Facebook");
        app.setPkgName("FB-Pkg");
        app.setNumBlocks(3);
        addApp(db, app);

        List<App> appList = db.appDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + appList.size());
    }

    private static Task addTask(final AppDatabase db, Task task) {
        db.taskDao().insertAll(task);
        return task;
    }

    private static void populateWithTestTask(AppDatabase db) {
        Task task = new Task();
        task.setTaskName("Running");
        task.setTaskComplete(false);
        task.setTaskTime(15);
        addTask(db, task);

        List<Task> taskList = db.taskDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + taskList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestApp(mDb);
            populateWithTestTask(mDb);
            return null;
        }

    }
}
