package com.AjouManagement.app.RoutineListActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.AjouManagement.*;

import androidx.appcompat.app.AppCompatActivity;

import com.AjouManagement.app.R;
import com.AjouManagement.app.TagData;
import com.AjouManagement.app.databinding.ActivityAddRoutineBinding;
import com.AjouManagement.app.databinding.ActivityRoutineListBinding;

import java.util.ArrayList;

//현문제 : 왜 애드루틴이 갖고 와지냐?
public class RoutineList extends AppCompatActivity {
    private ActivityRoutineListBinding binding;
    ArrayList<RoutineData> routineDataList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoutineListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.InitializedRoutineData();

        ListView listView = view.findViewById(R.id.list_routine);
        final RoutineAdapter routineAdapter = new RoutineAdapter(this, routineDataList);

        listView.setAdapter(routineAdapter);


    }

    public void InitializedRoutineData(){
        routineDataList = new ArrayList<RoutineData>();

        routineDataList.add(new RoutineData("2019-02-03","운동", "스트레칭", "10:00", "",""));
        routineDataList.add(new RoutineData("2023-02-06","자기계발, 어학, 공부", "토익 공부", "13:00", "월,수,금","2023-02-13"));
    }
}
