package com.AjouManagement.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.AjouManagement.app.databinding.ActivityAddRoutineBinding;

public class AddRoutine extends AppCompatActivity {
    private ActivityAddRoutineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRoutineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}