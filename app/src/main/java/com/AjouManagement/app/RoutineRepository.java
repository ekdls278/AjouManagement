package com.AjouManagement.app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RoutineRepository {
    private RoutineDBDao mRoutineDBDao;
    private LiveData<List<RoutineDBEntity>> mAllRoutines;
    private LiveData<List<RoutineDBEntity>> mTodayRoutines;

    RoutineRepository(Application application){
        RoutineDB db = RoutineDB.getDatabase(application);
        mRoutineDBDao = db.routineDBDao();
        mAllRoutines = mRoutineDBDao.getAll();
    }

    LiveData<List<RoutineDBEntity>> getAllRoutines(){
        return mAllRoutines;
    }
    LiveData<List<RoutineDBEntity>> getTodayRoutine(String routineDate){
        mTodayRoutines = mRoutineDBDao.getTodayRoutine(routineDate);
        return mTodayRoutines;
    }
    LiveData<List<RoutineDBEntity>> getTodayUnperformedRoutine(String routineDate){
        mTodayRoutines = mRoutineDBDao.getTodayUnperformedRoutine(routineDate);
        return mTodayRoutines;
    }

    void insert(RoutineDBEntity routineDBEntity) {
        RoutineDB.databaseWriteExecutor.execute(() -> {
            mRoutineDBDao.insert(routineDBEntity);
        });
    }
    void insertAll(RoutineDBEntity routineDBEntity) {
        RoutineDB.databaseWriteExecutor.execute(() -> {
            mRoutineDBDao.insert(routineDBEntity);
        });
    }

    void update(RoutineDBEntity routineDBEntity) {
        RoutineDB.databaseWriteExecutor.execute(() -> {
            mRoutineDBDao.update(routineDBEntity);
        });
    }
}
