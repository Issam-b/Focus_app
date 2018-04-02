package com.derassom.three_pixels.focus.utils;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;

import java.util.List;

public class DatabaseInit {

    private static final String TAG = DatabaseInit.class.getName();

    public static void populateAsync(@NonNull final FocusDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final FocusDatabase db) {
        populateWithTestApp(db);
        populateWithTestTask(db);
    }

    private static App addApp(final FocusDatabase db, App app) {
        db.appDao().insertAll(app);
        return app;
    }

    private static void populateWithTestApp(FocusDatabase db) {
        App app = new App();
        app.setAppName("Facebook");
        app.setPkgName("FB-Pkg");
        app.setAppDuration(10);
        app.setNumBlocks(3);
        app.setNumAllowed(1);
        app.setAppBonus(3);
        app.setNumOpen(5);
        addApp(db, app);

        List<App> appList = db.appDao().getAll();
        Log.d(DatabaseInit.TAG, "Rows Count: " + appList.size());
    }

    private static Task addTask(final FocusDatabase db, Task task) {
        db.taskDao().insertAll(task);
        return task;
    }

    private static void populateWithTestTask(FocusDatabase db) {
        Task task = new Task();
        task.setTaskName("Running");
        task.setTaskComplete(false);
        task.setTaskStartTime(15);
        task.setTaskEndTime(20);
        task.setTaskType("user");
        addTask(db, task);

        List<Task> taskList = db.taskDao().getAll();
        Log.d(DatabaseInit.TAG, "Rows Count: " + taskList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final FocusDatabase mDb;

        PopulateDbAsync(FocusDatabase db) {
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
