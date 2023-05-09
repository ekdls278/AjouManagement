package com.AjouManagement.app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder>
        implements ItemTouchHelperListener {
    private ArrayList<MainData> arrayList;

    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
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
        holder.tv_schedule_main.setText(arrayList.get(position).getTv_schedule_main());

        holder.itemView.setTag(position);
        //Click Event

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });


//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                remove(holder.getAdapterPosition());
//                return true;
//            }
//        });


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
        MainData item = arrayList.get(form_position);
        arrayList.remove(form_position);
        arrayList.add(to_position, item);
        notifyItemMoved(form_position, to_position);
        Log.d("onItemMove", "ItemMove");
        return true;
    }

//    @Override
//    public void onItemSwipe(int position, int direction) {
//        if(direction == ItemTouchHelper.START)
//        {
////            arrayList.remove(position);
////            notifyItemRemoved(position);
//            Log.d("onItemSwipe", "direction == START");
//        }
//        else if (direction == ItemTouchHelper.END)
//        {
//            Log.d("onItemSwipe", "direction==END");
//        }
//    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv_schedule_main;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_schedule_main = (TextView) itemView.findViewById(R.id.tv_schedule_main);
        }
    }
}
