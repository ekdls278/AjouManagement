package com.AjouManagement.app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoutineDBDao {
    @Query("SELECT * FROM routine")     //루틴 정보 전부 불러오기
    LiveData<List<RoutineDBEntity>> getAll();

    @Query("SELECT * FROM routine WHERE routine_date IN (:routineDate)")     //해당 날짜에 해당되는 루틴 불러오기
    LiveData<List<RoutineDBEntity>> getTodayRoutine(String routineDate);

//    @Query("SELECT * FROM routine WHERE routineId IN (:routineId)")
//    List<RoutineDBEntity> loadAllByIds(int[] routineId);

    @Query("SELECT * FROM routine WHERE routine_date IN (:routineDate) AND routine_perform_state = 0")
    LiveData<List<RoutineDBEntity>> getTodayUnperformedRoutine(String routineDate);

    @Insert
    void insertAll(RoutineDBEntity... dbEntities);

    @Insert     //루틴 추가하기
    void insert(RoutineDBEntity routineDBEntity);

    @Update     //수행 여부 업데이트하기
    void update(RoutineDBEntity routineDBEntity);

    @Delete
    void delete(RoutineDBEntity data);


}
