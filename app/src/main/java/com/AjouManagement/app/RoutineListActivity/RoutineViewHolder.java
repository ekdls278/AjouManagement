package com.AjouManagement.app.RoutineListActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.AjouManagement.app.R;

public class RoutineViewHolder extends RecyclerView.ViewHolder {
    private final TextView routineItemView;
    private RoutineViewHolder(View itemView) {
        super(itemView);
        routineItemView = itemView.findViewById(R.id.title_list);
    }
    public void bind(String text) {
        routineItemView.setText(text);
    }



    static RoutineViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_routine_list, parent, false);
        return new RoutineViewHolder(view);
    }
}
