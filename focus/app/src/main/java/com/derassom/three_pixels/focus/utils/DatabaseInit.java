package com.derassom.three_pixels.focus.utils;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;

import java.util.List;

public class DatabaseInit {

    private static final String TAG = "DatabaseInit";

    public static void populateAsync(@NonNull final FocusDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final FocusDatabase db) {
        populateWithTestApp(db);
        populateWithTestTask(db);
    }

    public static App addApp(final FocusDatabase db, App app) {
        db.appDao().insertAll(app);
        return app;
    }

    public static void populateWithTestApp(FocusDatabase db) {
        App facebook =new App();
        facebook.setPkgName("com.facebook.katana");
        facebook.setAppName("Facebook");
        facebook.setTimeAllowed(7200000);// 2 Hours
        facebook.setEnabled(false);
        addApp(db,facebook);
        App instagram =new App();
        instagram.setPkgName("com.instagram.android");
        instagram.setAppName("Instagram");
        instagram.setTimeAllowed(7200000);// 2 Hours
        instagram.setEnabled(false);
        addApp(db,instagram);
        App messenger =new App();
        messenger.setPkgName("com.facebook.orca");
        messenger.setAppName("Messenger");
        messenger.setEnabled(false);
        messenger.setTimeAllowed(7200000);// 2 Hours
        addApp(db,messenger);
        List<App> appList = db.appDao().getAll();

        for(int i=0;i<appList.size();i++)
        {
            Log.d(TAG, String.valueOf(appList.get(i)));
        }

        Log.d(DatabaseInit.TAG, "Rows Count: " + appList.size());
    }

    public static void printApps(FocusDatabase db) {

        List<App> appList = db.appDao().getAll();

        for(int i=0;i<appList.size();i++)
        {
            Log.d(TAG, String.valueOf(appList.get(i)));
        }

        Log.d(DatabaseInit.TAG, "Rows Count: " + appList.size());
    }

    public static void printTasks(FocusDatabase db) {

        List<Task> appList = db.taskDao().getAll();

        for(int i=0;i<appList.size();i++)
        {
            Log.d(TAG, appList.get(i).toString());
        }

        Log.d(DatabaseInit.TAG, "Rows Count: " + appList.size());
    }

    private static Task addTask(final FocusDatabase db, Task task) {
        db.taskDao().insertAll(task);
        return task;
    }

    public static void populateWithTestTask(FocusDatabase db) {

        Task task1=new Task();
        task1.setTaskName("Practise Sport for 1 hour");

        addTask(db, task1);
        Task task2=new Task();
        task2.setTaskName("Stay away from Phone for 2 hours");
        addTask(db, task2);
        Task task3=new Task();
        task3.setTaskName("Read 10 pages from a book");
        addTask(db, task3);
        Task task4=new Task();
        task4.setTaskName("Go outside with your Friends");
        addTask(db, task4);

        List<Task> taskList = db.taskDao().getAll();

        for(int i=0;i<taskList.size();i++)
        {
            Log.d(TAG, String.valueOf(taskList.get(i)));
        }

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
