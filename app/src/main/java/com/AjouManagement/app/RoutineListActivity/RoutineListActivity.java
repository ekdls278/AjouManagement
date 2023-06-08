package com.AjouManagement.app.RoutineListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.AjouManagement.app.AddRoutineActivity;
import com.AjouManagement.app.R;
import com.AjouManagement.app.RoutineDBEntity;
import com.AjouManagement.app.RoutineViewModel;
import com.AjouManagement.app.databinding.ActivityRoutineListBinding;

import java.util.ArrayList;
import java.util.List;

public class RoutineListActivity extends AppCompatActivity {
    private ActivityRoutineListBinding binding;
    LiveData<List<RoutineDBEntity>> routineDataList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoutineListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //모든 루틴 리스트 갖고오기
        RoutineViewModel viewModel= new ViewModelProvider(this).get(RoutineViewModel.class);        //쿼리날릴 뷰모델 선언
        routineDataList = viewModel.getAllRoutines();       //모든 루틴 가져오기

        RoutineAdapter routineAdapter = new RoutineAdapter(routineDataList.getValue());

        viewModel.getAllRoutines().observe(this, new Observer<List<RoutineDBEntity>>() {
            @Override
            public void onChanged(List<RoutineDBEntity> routineDBEntities) {
                routineAdapter.setRoutineList(routineDBEntities);
            }
        });

        //특정 날짜에 해당하는 루틴 리스트 갖고오기
        /*
        RoutineViewModel viewModel= new ViewModelProvider(this).get(RoutineViewModel.class);        //쿼리날릴 뷰모델 선언
        routineDataList = viewModel.getTodayRoutines("2023/06/03");

        RoutineAdapter routineAdapter = new RoutineAdapter(routineDataList.getValue());

        viewModel.getTodayRoutines("2023/06/03").observe(this, new Observer<List<RoutineDBEntity>>() {
            @Override
            public void onChanged(List<RoutineDBEntity> routineDBEntities) {
                routineAdapter.setRoutineList(routineDBEntities);
            }
        });*/

        RecyclerView recyclerView = view.findViewById(R.id.list_routine); //리사이클러뷰 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(routineAdapter);

    }
    
    public void moveToAddRoutine(View view){
        Intent intent = new Intent(getApplicationContext(), AddRoutineActivity.class);
        startActivity(intent);
    }

    /* 루틴 데이터 업데이트
    public void update(){
        RoutineViewModel viewModel= new ViewModelProvider(this).get(RoutineViewModel.class);        //쿼리날릴 뷰모델 선언
        RoutineDBEntity updateData = new RoutineDBEntity();
        updateData.routineId = 3;
        updateData.routineTitle = "bu";
        updateData.routineDate = "2023/06/03";
        updateData.routineTime = "15:02";
        updateData.routineRepeatEndDate = "2023/06/25";
        updateData.routineRepeatDayOfWeek = "화";
        updateData.routinePerformState = 1;         //수행 상태 완료로 변경
        viewModel.update(updateData);
    }

     */
}