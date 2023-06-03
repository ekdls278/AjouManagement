package com.AjouManagement.app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoutineDBDao {
    @Query("SELECT * FROM routine")     //루틴 정보 전부 불러오기 //여기가 안불러와진다고...
    LiveData<List<RoutineDBEntity>> getAll();

    @Query("SELECT routine_title FROM routine")     //루틴 이름 전부 불러오기
    LiveData<List<String>> getTitleAll();

//    @Query("SELECT * FROM routine WHERE routineId IN (:routineId)")
//    List<RoutineDBEntity> loadAllByIds(int[] routineId);

    @Insert
    void insertAll(RoutineDBEntity... dbEntities);

    @Insert     //루틴 추가 => 충돌체크 어떻게 할지?
    void insert(RoutineDBEntity routineDBEntity);

    @Delete
    void delete(RoutineDBEntity data);
}
