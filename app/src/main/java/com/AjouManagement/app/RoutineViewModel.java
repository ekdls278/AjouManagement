package com.AjouManagement.app;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {
    private RoutineRepository mRepository;
    private final LiveData<List<RoutineDBEntity>> mAllRoutines;
    private LiveData<List<RoutineDBEntity>> mTodayRoutines;
    public RoutineViewModel (Application application){
        super(application);
        mRepository = new RoutineRepository(application);
        mAllRoutines = mRepository.getAllRoutines();
    }
    public LiveData<List<RoutineDBEntity>> getAllRoutines(){ return mAllRoutines; }
    public LiveData<List<RoutineDBEntity>> getTodayRoutines(String routineDate){
        mTodayRoutines = mRepository.getTodayRoutine(routineDate);
        return mTodayRoutines;
    }
    public LiveData<List<RoutineDBEntity>> getTodayUnperformedRoutine(String routineDate){
        mTodayRoutines = mRepository.getTodayUnperformedRoutine(routineDate);
        return mTodayRoutines;
    }
    public void insert(RoutineDBEntity routineDBEntity) { mRepository.insert(routineDBEntity); }
    public void update(RoutineDBEntity routineDBEntity) { mRepository.update(routineDBEntity); }
}
