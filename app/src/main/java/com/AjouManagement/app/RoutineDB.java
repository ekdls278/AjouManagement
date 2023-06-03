package com.AjouManagement.app;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {RoutineDBEntity.class}, version = 1)
public abstract class RoutineDB extends RoomDatabase {

    public abstract RoutineDBDao routineDBDao();
    private static volatile RoutineDB INSTANCE;


    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoutineDB getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RoutineDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RoutineDB.class, "routine_db").allowMainThreadQueries()
                            .build();
                }

            }
        }
        return INSTANCE;
    }

    /*
    public static void destroyInstance(){
        INSTANCE = null;
    }
    */
}
