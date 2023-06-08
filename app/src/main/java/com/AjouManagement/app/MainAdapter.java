package com.AjouManagement.app;

import android.content.ClipData;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder>
        implements ItemTouchHelperListener, View.OnLongClickListener {
    private List<RoutineDBEntity> arrayList;
    private DragListener.Listener listener;

    public MainAdapter(List<RoutineDBEntity> arrayList, DragListener.Listener listener) {
        this.arrayList = arrayList;
        this.listener = listener;
    }
    public void setMainList(List<RoutineDBEntity> data){
        arrayList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_main, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        holder.tv_schedule_main.setText(arrayList.get(position).getTitle());
        holder.itemView.setTag(position);

        holder.frameLayout.setOnLongClickListener(this);
        holder.frameLayout.setOnDragListener(new DragListener(listener));
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onItemMove(int form_position, int to_position) {
        RoutineDBEntity item = arrayList.get(form_position);
        arrayList.remove(form_position);
        arrayList.add(to_position, item);
        notifyItemMoved(form_position, to_position);
        Log.d("onItemMove", "ItemMove");
        return true;
    }



    void updateList(List<RoutineDBEntity> arrayList) {
        this.arrayList = arrayList;
    }

    DragListener getDragInstance() {
        if (listener != null) {
            return new DragListener(listener);
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }
    List<RoutineDBEntity> getList() {
        return arrayList;
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data, shadowBuilder, v, 0);
        } else {
            v.startDrag(data, shadowBuilder, v, 0);
        }
        return true;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected FrameLayout frameLayout;
        protected TextView tv_schedule_main;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_schedule_main = (TextView) itemView.findViewById(R.id.tv_schedule_main);
            this.frameLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_item);
        }
    }
}
