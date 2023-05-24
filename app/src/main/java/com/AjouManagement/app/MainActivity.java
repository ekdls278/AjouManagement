package com.AjouManagement.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.StackView;

import com.AjouManagement.app.databinding.ActivityMainBinding;
import com.amyu.stack_card_layout_manager.StackCardLayoutManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DragListener.Listener {
    private ActivityMainBinding binding;

    private ArrayList<MainData> arrayList;
    private ArrayList<MainData> addArrayList;

    private MainAdapter mainAdapter;
    private MainAdapter addAdapter;

    private RecyclerView recyclerView;
    private RecyclerView addRecyclerView;

    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager addLayoutManager;

    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper addItemTouchHelper;

    private FallingBall fallingBall;

    private boolean isVisible = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.rvAddRoutine.setVisibility(View.GONE);
        setContentView(view);
        initMainRecyclerView();
        initAddRecyclerView();
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
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible){
                    showAddList();
                    binding.btnAddRoutine.setVisibility(View.GONE);
                }else{
                    hideAddList();
                    binding.btnAddRoutine.setVisibility(View.VISIBLE);
                }

            }
        });

    }
    private void initMainRecyclerView(){
        //Main RecyclerView 설정
        recyclerView = binding.rv;

        linearLayoutManager =  new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MainItemDecoration(-150));
        arrayList = new ArrayList<>();

        for(int i=0; i<20; i++){
            MainData testData = new MainData("TestData" +i);
            arrayList.add(testData);
        }
        Collections.reverse(arrayList);

        //Main RecyclerView Adapter설정
        mainAdapter = new MainAdapter(arrayList, this);
        recyclerView.setAdapter(mainAdapter);

        fallingBall = binding.fb;
        if(isVisible){
            recyclerView.setOnDragListener(mainAdapter.getDragInstance());
        }
        else{
            recyclerView.setOnDragListener(null);
        }

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mainAdapter, fallingBall, true));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initAddRecyclerView(){
        //Add RecyclerView 설정
        addRecyclerView = binding.rvAddRoutine;
        addLayoutManager = new LinearLayoutManager(this);
        addRecyclerView.setLayoutManager(addLayoutManager);
        addArrayList = new ArrayList<>();

        for(int i=0; i<5; i++){
            MainData testData = new MainData("Data" +i);
            addArrayList.add(testData);
        }
        addAdapter = new MainAdapter(addArrayList, this);
        addRecyclerView.setAdapter(addAdapter);

        addRecyclerView.setOnDragListener(addAdapter.getDragInstance());

        addItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(addAdapter, fallingBall, false));
        addItemTouchHelper.attachToRecyclerView(addRecyclerView);
    }


    //Add RecyclerView show/hide
    private void showAddList(){
        addRecyclerView.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(-addRecyclerView.getWidth(), 0, 0, 0);
        animation.setDuration(500);
        addRecyclerView.startAnimation(animation);
        isVisible = false;
    }
    private void hideAddList() {
        TranslateAnimation animation = new TranslateAnimation(0, -addRecyclerView.getWidth(), 0, 0);
        animation.setDuration(500);
        addRecyclerView.startAnimation(animation);
        addRecyclerView.setVisibility(View.GONE);
        isVisible = true;
    }


    //List가 비었을 때 Text표시
    @Override
    public void setEmptyListTop(boolean visibility) {

    }

    @Override
    public void setEmptyListBottom(boolean visibility) {

    }
}