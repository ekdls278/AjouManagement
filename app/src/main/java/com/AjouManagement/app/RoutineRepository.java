package com.AjouManagement.app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RoutineRepository {
    private RoutineDBDao mRoutineDBDao;
    private LiveData<List<RoutineDBEntity>> mAllRoutines;

    RoutineRepository(Application application){
        RoutineDB db = RoutineDB.getDatabase(application);  //여기 디비가 안불러와지는거같은데
        mRoutineDBDao = db.routineDBDao();
        mAllRoutines = mRoutineDBDao.getAll();
    }

    LiveData<List<RoutineDBEntity>> getAllRoutines(){
        return mAllRoutines;
    }

    void insert(RoutineDBEntity routineDBEntity) {
        RoutineDB.databaseWriteExecutor.execute(() -> {
            mRoutineDBDao.insert(routineDBEntity);
        });
    }
}
