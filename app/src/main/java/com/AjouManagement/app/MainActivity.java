package com.AjouManagement.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.StackView;

import com.AjouManagement.app.databinding.ActivityMainBinding;
import com.amyu.stack_card_layout_manager.StackCardLayoutManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnAddRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddRoutineActivity.class);
                startActivity(intent);
            }
        });

        binding.btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = binding.rv;


        linearLayoutManager =  new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MainItemDecoration(-150));
        arrayList = new ArrayList<>();

        for(int i=0; i<20; i++){
            MainData testData = new MainData("TestData" +i);
            arrayList.add(testData);
        }

        Collections.reverse(arrayList);


        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mainAdapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);




    }


}