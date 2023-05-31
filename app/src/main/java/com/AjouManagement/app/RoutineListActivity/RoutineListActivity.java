package com.AjouManagement.app.RoutineListActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


        RoutineViewModel viewModel= new ViewModelProvider(this).get(RoutineViewModel.class);
        routineDataList = viewModel.getAllRoutines();

        RoutineAdapter routineAdapter = new RoutineAdapter(routineDataList.getValue()); //어댑터 안에는 db 데이터가 들어와야함

        viewModel.getAllRoutines().observe(this, new Observer<List<RoutineDBEntity>>() {
            @Override
            public void onChanged(List<RoutineDBEntity> routineDBEntities) {
                routineAdapter.setRoutineList(routineDBEntities);
            }
        });
//        this.InitializedRoutineData();

        RecyclerView recyclerView = view.findViewById(R.id.list_routine); //리사이클러뷰 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setAdapter(routineAdapter);

    }
/*
    public void InitializedRoutineData(){
        routineDataList = new ArrayList<RoutineData>();

        routineDataList.add(new RoutineData("2019-02-03","운동", "스트레칭", "10:00", "",""));
        routineDataList.add(new RoutineData("2023-02-06","자기계발, 어학, 공부", "토익 공부", "13:00", "월,수,금","2023-02-13"));
    }*/
}
