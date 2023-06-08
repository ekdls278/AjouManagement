package com.AjouManagement.app.RoutineListActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.AjouManagement.app.R;
import com.AjouManagement.app.RoutineDB;
import com.AjouManagement.app.RoutineDBEntity;

import java.util.ArrayList;
import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {       //현문제: 뷰가 하나밖에 안뜸
    private List<RoutineDBEntity> routineList = null;
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView tag;
        TextView date;
        TextView time;
        TextView repeatDow;
        TextView repeatEnd;
        ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title_list);
            tag = view.findViewById(R.id.tag_list);
            date = view.findViewById(R.id.selectedDate_list);
            time = view.findViewById(R.id.selectedTime_list);
            repeatDow = view.findViewById(R.id.repeatDOW_list);
            repeatEnd = view.findViewById(R.id.repeatEndDate_list);
        }
    }
    RoutineAdapter(List<RoutineDBEntity> list){
        routineList = list;
    }
    @Override
    public RoutineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.routine, parent, false);
        RoutineAdapter.ViewHolder vh = new RoutineAdapter.ViewHolder(view);

        return vh;
    }
    @Override
    public void onBindViewHolder(RoutineAdapter.ViewHolder holder, int position) {
        if(routineList.get(position).routineTitle != null)
            holder.title.setText(routineList.get(position).routineTitle);
        if(routineList.get(position).routineTag != null)
            holder.tag.setText(routineList.get(position).routineTag);
        if(routineList.get(position).routineDate != null)
            holder.date.setText(routineList.get(position).routineDate);
        if(routineList.get(position).routineTime != null)
            holder.time.setText(routineList.get(position).routineTime);
        if(routineList.get(position).routineRepeatDayOfWeek != null)
            holder.repeatDow.setText(routineList.get(position).routineRepeatDayOfWeek);
        if(routineList.get(position).routineRepeatEndDate != null)
            holder.repeatEnd.setText(routineList.get(position).routineRepeatEndDate);
    }

    @Override
    public int getItemCount() {
        return (null != routineList ? routineList.size() :0);
    }

    public void setRoutineList(List<RoutineDBEntity> data){
        routineList = data;
        notifyDataSetChanged();
    }
}
/*
public class RoutineAdapter extends ListAdapter<RoutineDBEntity, RoutineViewHolder> {
    public RoutineAdapter(@NonNull DiffUtil.ItemCallback<RoutineDBEntity> diffCallback) {
        super(diffCallback);
    }
    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RoutineViewHolder.create(parent);
    }
    @Override
    public void onBindViewHolder(RoutineViewHolder holder, int position) {
        RoutineDBEntity current = getItem(position);
        holder.bind(current.getTitle());
    }
    static class WordDiff extends DiffUtil.ItemCallback<RoutineDBEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull RoutineDBEntity oldItem, @NonNull RoutineDBEntity newItem) {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull RoutineDBEntity oldItem, @NonNull RoutineDBEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }
}
*/

/*
Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<RoutineData> Routine;

    public RoutineAdapter(Context context, ArrayList<RoutineData> data){
        mContext = context;
        Routine = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return Routine.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RoutineData getItem(int position) {
        return Routine.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {      //여기만 좀 수정하기

        View view = mLayoutInflater.inflate(R.layout.routine, null);

        TextView title = (TextView)view.findViewById(R.id.title_list);
        TextView selectedDate = (TextView)view.findViewById(R.id.selectedDate_list);
        TextView tag = (TextView)view.findViewById(R.id.tag_list);
        TextView selectedTime = (TextView)view.findViewById(R.id.selectedTime_list);
        TextView repeatDoW = (TextView)view.findViewById(R.id.repeatDOW_list);
        TextView repeatEndDate = (TextView)view.findViewById(R.id.repeatEndDate_list);

        title.setText(Routine.get(position).getTitle());
        selectedDate.setText("수행 날짜: "+Routine.get(position).getSelectedDate());
        tag.setText(Routine.get(position).getTag());
        selectedTime.setText("수행 시간: "+Routine.get(position).getselectedTime());
        repeatDoW.setText("반복 시간: "+Routine.get(position).getrepeatDoW());
        repeatEndDate.setText("종료 날짜: "+Routine.get(position).getrepeatEndDate());

        return view;
    }
* */