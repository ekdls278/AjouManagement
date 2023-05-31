package com.AjouManagement.app;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {
    private RoutineRepository mRepository;
    private final LiveData<List<RoutineDBEntity>> mAllRoutines;
    public RoutineViewModel (Application application){
        super(application);
        mRepository = new RoutineRepository(application);
        mAllRoutines = mRepository.getAllRoutines();
    }
    public LiveData<List<RoutineDBEntity>> getAllRoutines(){ return mAllRoutines; }

    public void insert(RoutineDBEntity routineDBEntity) { mRepository.insert(routineDBEntity); }

}
