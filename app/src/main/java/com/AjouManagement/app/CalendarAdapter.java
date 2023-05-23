package com.AjouManagement.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<DayItem> everyDate;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<DayItem> daysOfMonth, OnItemListener onItemListener)
    {
        this.everyDate = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int)(parent.getHeight()*0.1666666);
        return new CalendarViewHolder(view,onItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        DayItem dayItem = everyDate.get(position);
        if (dayItem != null) {
            holder.bindData(dayItem);
        } else {
            holder.clearData();
        }
    }


    public int getItemCount() {return everyDate.size();}

    public DayItem getItem(int position) {return everyDate.get(position);}
    public interface OnItemListener {
        void onItemClick(int position,String dayText);
    }
}
