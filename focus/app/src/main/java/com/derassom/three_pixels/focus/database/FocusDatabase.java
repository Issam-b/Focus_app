package com.derassom.three_pixels.focus.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.derassom.three_pixels.focus.dao.AppDao;
import com.derassom.three_pixels.focus.dao.TaskDao;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;

@Database(entities = {App.class, Task.class}, version = 1)
public abstract class FocusDatabase extends RoomDatabase {

    private static FocusDatabase INSTANCE;

    public abstract AppDao appDao();
    public abstract TaskDao taskDao();

    public static FocusDatabase getFocusDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), FocusDatabase.class, "focus-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
