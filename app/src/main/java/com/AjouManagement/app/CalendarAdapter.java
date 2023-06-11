package com.AjouManagement.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//
public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<DayItem> everyDate;
    private List<RoutineDBEntity> routineList = null;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<DayItem> daysOfMonth, OnItemListener onItemListener,List<RoutineDBEntity> routineDBEntityList) {
        this.everyDate = daysOfMonth;
        this.onItemListener = onItemListener;
        this.routineList = routineDBEntityList;
    }


    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int)(parent.getHeight()*0.1777777);
        return new CalendarViewHolder(view,onItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        DayItem dayItem = everyDate.get(position);
        if (dayItem != null) {
            holder.bindData(dayItem, routineList);
        } else {
            holder.clearData();
        }
    }
    public int getItemCount() {
//        return everyDate.size();
        return (null != everyDate ? everyDate.size() : 0);
    }

    public interface OnItemListener {
        void onItemClick(int position,String dayText);
    }
}
